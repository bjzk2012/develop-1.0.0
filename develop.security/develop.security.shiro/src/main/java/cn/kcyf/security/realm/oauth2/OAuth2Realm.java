package cn.kcyf.security.realm.oauth2;

import cn.kcyf.security.domain.OAuth2Token;
import cn.kcyf.security.domain.ShiroUser;
import cn.kcyf.security.enumerate.LoginType;
import cn.kcyf.security.realm.BasicRealm;
import cn.kcyf.security.service.ShiroService;
import com.alibaba.fastjson.JSONObject;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.springframework.beans.factory.annotation.Autowired;

public class OAuth2Realm extends BasicRealm {

    private String clientId;
    private String clientSecret;
    private String accessTokenUrl;
    private String profileUrl;
    private String redirectUrl;
    @Autowired
    private ShiroService shiroService;

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public void setAccessTokenUrl(String accessTokenUrl) {
        this.accessTokenUrl = accessTokenUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        OAuth2Token oAuth2Token = (OAuth2Token) token;
        String code = oAuth2Token.getAuthCode();
        ShiroUser shiroUser = extractUser(code, oAuth2Token.getDomain());

        SimpleAuthenticationInfo authenticationInfo =
                new SimpleAuthenticationInfo(shiroUser, code, getName());
        return authenticationInfo;
    }

    private ShiroUser extractUser(String code, String domain) {
        try {
            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
            OAuthClientRequest accessTokenRequest = OAuthClientRequest
                    .tokenLocation(accessTokenUrl)
                    .setGrantType(GrantType.AUTHORIZATION_CODE)
                    .setClientId(clientId)
                    .setClientSecret(clientSecret)
                    .setCode(code)
                    .setRedirectURI(domain + redirectUrl)
                    .buildQueryMessage();
            OAuthAccessTokenResponse oAuthResponse = oAuthClient.accessToken(accessTokenRequest, OAuth.HttpMethod.POST);
            String accessToken = oAuthResponse.getAccessToken();
            OAuthClientRequest profileRequest = new OAuthBearerClientRequest(profileUrl)
                    .setAccessToken(accessToken).buildQueryMessage();
            OAuthResourceResponse profileResponse = oAuthClient.resource(profileRequest, OAuth.HttpMethod.GET, OAuthResourceResponse.class);
            String body = profileResponse.getBody();
            JSONObject result = JSONObject.parseObject(body);
            return shiroService.checkUser(result, LoginType.OAUTH2);
        } catch (Exception e) {
            throw new RuntimeException("Oauth认证异常", e);
        }
    }
}
