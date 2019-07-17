![](https://images.contentstack.io/v3/assets/bltefdd0b53724fa2ce/bltaeb7a6a4e9dc3735/5d07f07ddc3c021053898fa7/logo-kibana-24-color.svg) Kibana

- - -
## 一、Elasticsearch安装启动，详见[ES介绍](../../develop.orm/develop.orm.es/README.md)。
## 二、安装logstash
* 下载logstash-5.6.0.tar.gz

    ```wget https://artifacts.elastic.co/downloads/logstash/logstash-7.2.0.tar.gz```
* 解压logstash-5.6.0.tar.gz

    ```tar -xzf logstash-5.6.0.tar.gz ```
* bin目录下新建配置log4j_to_es.conf
    ```input {
            tcp {  
                #host:port就是上面appender中的 destination，这里其实把logstash作为服务，开启8080端口接收logback发出的消息
                host => "localhost"
                port => 8088
                #模式选择为server
                mode => "server"
                tags => ["tags"]
                ##格式json
                codec => json_lines
            }
        } 
        
        output {
            elasticsearch {
                #ES地址
                hosts => "127.0.0.1:9200"
                #指定索引名字
                index => "applog"
            }
            stdout { codec => rubydebug}
        }
    ```
* 运行

    ```logstash -f log4j_to_es.conf```
## 三、安装kibana
* 下载kibana-5.6.0-linux-x86_64.tar.gz

    ```wget https://artifacts.elastic.co/downloads/kibana/kibana-5.6.0-linux-x86_64.tar.gz```
* 解压kibana-5.6.0-linux-x86_64.tar.gz

    ```kibana-5.6.0-linux-x86_64.tar.gz```
* 修改配置文件

    ```cd kibana-5.6.0\config```
    
    ```vim kibana.yml```
    
    添加
    
    ```elasticsearch.url: "http://127.0.0.1:9200"```
    
    > 此处"http://127.0.0.1:9200"可写为["http://127.0.0.1:9200", "http://127.0.0.1:9201"]作为集群```
* 启动kibana

    ```./kibana```
