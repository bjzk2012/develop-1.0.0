![](https://lucene.apache.org/images/mantle-lucene-solr.png)

- - -
## 一、安装Solr

* 创建Solr用户solrcloud

    ```useradd solrcloud```

* 下载solr-7.6.0.tgz

    ```cd /home/solrcloud```

    ```wget https://mirrors.tuna.tsinghua.edu.cn/apache/lucene/solr/7.6.0/solr-7.6.0.tgz```

* 解压solr-7.6.0.tgz

    ```cd /home/solrcloud```
    
    ```tar zxvf  solr-7.6.0.tgz -C ./```

    > 注：以下$SOLR_HOME代表/home/solrcloud/solr-7.6.0目录

* 删除默认的core

    ```cd $SOLR_HOME/server/solr```
    
    ```rm -rf configsets```
    
* 创建一个自己的core

    * 创建文件夹

    ```mkdir document```

    * 通过模板创建

    ```cp -r $SOLR_HOME/example/example-DIH/solr/solr/* document```

    * core.properties配置

    ```cd $SOLR_HOME/server/solr/document```
    
    ```vim core.properties```
    
    > 修改或添加name=document

    > 在document下创建data目录并赋予777权限

    ```cd document```
    
    ```mkdir data```
    
    ```chmod 777 data```
    
    > 注：红色部分根据自己的core名字命名
    
 * 日志配置
 
    > 在server下创建logs目录并赋予777权限
    
    ```cd $SOLR_HOME/server```
                               
    ```mkdir logs```
                     
    ```chmod 777 logs```
                         
* 使用solrcloud用户启动solr服务

    ```su solrcloud```
                    
    ```cd $SOLR_HOME/bin```
                         
    ```./solr start```
                    
## 二、导入索引

   > 创建db-data-config.xml放在$SOLR_HOME/server/solr/document/conf下添加
    
```
    <dataConfig>
        <dataSource name="ds_xatrm_solr" driver="com.mysql.jdbc.Driver" url="jdbc:mysql://#:3306/solr_datasouce" user="root" password="root" />
        <dataSource name="ds_bindfile_bz" type="BinFileDataSource" basicPath="${solr.install.dir}/server/solr-webapp/webapp/upload"/>
        <document>
            <!-- 新闻 -->
            <entity name="xatrm_xatrm_news" dataSource="ds_xatrm_solr" query="[sql语句]" deltaQuery = "[sql语句]">
                <field column="id" name="id"/>
                <field column="text" name="text"/>
                <field column="author" name="author"/>
                <field column="title" name="title"/>
                <field column="create_time" name="create_time"/>
                <field column="last_modified" name="last_modified"/>
                <field column="type" name="type"/>
                <field column="path" name="path"/>
                <field column="site" name="site"/>
            </entity>
        </document>
    </dataConfig>
```
    
   > 在$SOLR_HOME/server/solr/document/conf下的solrconfig.xml文件中，查询到requestHandler标签的位置，添加以下代码如图：
    
```
    <requestHandler name="/dataimport" class="org.apache.solr.handler.dataimport.DataImportHandler">
          <lst name="defaults">
             <str name="config">db-data-config.xml</str>
          </lst>
    </requestHandler>
```
    
   > 将solr-7.6.0\dist下的solr-dataimporthandler-7.6.0.jar和mysql驱动包mysql-connector-java-5.1.25.jar，放入$SOLR_HOME\server\solr-webapp\WEB-INF\lib文件夹下；
    
   > 重启solr
    
   > 访问http://localhost:8983/solr/#/document/dataimport/dataimport按下图进行数据导入
 