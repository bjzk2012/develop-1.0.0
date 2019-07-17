![](https://static-www.elastic.co/v3/assets/bltefdd0b53724fa2ce/blt280217a63b82a734/5bbdaacf63ed239936a7dd56/elastic-logo.svg)
- - -
##一、安装Es
### 1 单机单实例
* 1.1 安装Elasticsearch

    * 1.1.1	下载
    
        ```下载地址：https://www.elastic.co/downloads/past-releases/elasticsearch-2-3-4```
        
        ```下载elasticsearch-2.3.4.zip放到/usr/software目录下```
        
        ```Elasticsearch与操作系统或jvm的兼容矩阵见：https://www.elastic.co/support/matrix```
    * 1.1.2	解压
        * 将elasticsearch-2.3.4.zip解压放到$ES_HOME下
         
    * 1.1.3	运行
    
        > 本文使用root用户启动es服务，启动的前提是bin/elasticsearch中加入ES_JAVA_OPTS="-Des.insecure.allow.root=true"，否则需要在启动命令后追加，如$ES_HOME/bin/elasticsearch -Des.insecure.allow.root=true。    
    
        * 有两种启动方式（与之对应的关闭方式），两者的区别在于命令中是否加入 -d：
        
            > ①在console中启动，随时可以监控启动加载的日志；
            
            ```$ES_HOME/bin/elasticsearch```
            
            ```关闭的话，直接在console中Ctrl+C 即可停止服务。```
    
            > ②后台启动，启动log可以在es的安装目录中找到
            
            ```$ES_HOME/ bin/elasticsearch -d```
            
            ```关闭的话，先找到es占用的pid，可用以下几种方式：```
		 
    * 1.1.4	修改外部访问限制
        > 为了使外部能够访问Elasticsearch服务，需要修改${you_es_root}/config/elasticsearch.yml文件，在最后加上：
        
        ```network.host: 0.0.0.0```

* 1.2 安装elasticsearch-jdbc

    * 1.2.1 下载elasticsearch-jdbc-master
        > elasticsearch-jdbc-master.zip（包含一些安装部署的指导）。安装包放到/usr/software目录下。下载地址：https://github.com/jprante/elasticsearch-jdbc/archive/master.zip ，版本是目前github的最新版本：2.3.4.1，兼容elasticsearch-2.3.4。
        解压到/usr/es/elasticsearch-jdbc-master。
    
    * 1.2.2	 下载elasticsearch-jdbc-dist
    
        > elasticsearch-jdbc-2.3.4.1-dist.zip（包含必须的jar包文件）。下载地址：http://xbib.org/repository/org/xbib/elasticsearch/importer/elasticsearch-jdbc/2.3.4.1/elasticsearch-jdbc-2.3.4.1-dist.zip。
    * 1.2.3	 拷贝lib
        > 解压elasticsearch-jdbc-2.3.4.1-dist.zip，将lib包copy到/usr/es/elasticsearch-jdbc-master/下。
        elasticsearch-jdbc-master的目录结构为（其中lib目录下有45个jar）：
         
    * 1.2.4	设置环境变量
        > 设置Elasticsearch jdbc环境变量，在profile文件中加入环境变量JDBC_IMPORTER_HOME，并使其生效。
    
        ```
        vi /etc/profile
        export JDBC_IMPORTER_HOME=/usr/es/elasticsearch-jdbc-master
        source /etc/profile
        ```

* 1.3	安装head插件
    
	> Elasticsearch安装完成之后，希望能有一个可视化的环境来操作它。到ES的安装目录即$ES_HOME下，进到bin目录下，执行：
	
	```./plugin install mobz/elasticsearch-head```

	> 安装完成，可以访问：http://localhost:9200/_plugin/head/，可以查看索引信息、浏览数据、查询等。
	 
	 
     

* 1.4	安装ik分词插件
    * 1.4.1	下载
        > 下载地址：https://github.com/medcl/elasticsearch-analysis-ik/releases。下载elasticsearch-analysis-ik-1.9.4.zip，兼容elasticsearch-2.3.4。
    * 1.4.2	解压
        > 解压到/usr/es/elasticsearch-analysis-ik-1.9.4，其中注意：content是pre-build 即 预编译的代码，需要编译之后放到elasticsearch中，否则运行不了。
    
        > 插播：想偷懒的可以下载medcl的elasticsearch-RTF直接使用，里面需要的插件和配置基本都已经设置好。（此法目前还未验证过）
    
    * 1.4.3	编译
        > 用mvn编译打包：
        ```
        cd /usr/es/elasticsearch-analysis-ik-1.9.4
        mvn package
        ```
        > 经过compile，target目录下会生成如下文件：			 
    * 1.4.4	安装
        > 拷贝+解压 target/releases/elasticsearch-analysis-ik-1.9.4.zip 到 ${your-es-root}/plugins/ik。即拷贝到$ES_HOME/plugins/ik 文件夹下。形成的文档结构如下，其中5个jar包是解压而得：
     
        > 拷贝+解压完成之后，若Elasticsearch正在启动着，则需要重启。
    
    * 1.4.5	测试分词是否可用
        > 在浏览器键入：http://localhost:9200/_analyze?analyzer=ik&pretty=true&text=helloworld,中华人民共和国，看是否分词。
    
    * 1.4.6	设置es的默认分词策略
        > 在${your-es-root}/config/elasticsearch.yml后面添加一行：
        ```index.analysis.analyzer.default.type : "ik"```

* 1.5	ES数据生成
    * 1.5.1	create index
        > 创建索引可以利用head插件的【复合查询】来实现，如果生成名称为：test419的索引，则：/test419  PUT
        > 提示{"acknowledged":true} 表示生成成功。
        
        > 当然也可以通过java程序来实现生成索引的目的。
        
        > 生成完成后，可以在概览页签看到名为：test419的索引信息：
    
    * 1.5.2	create a mapping
        > 创建mapping这一步是必须的，否则在中文环境下，分词结果就会差强人意。elasticsearch默认采用的是standard，对英文比较试用。
     
        > 具体运行的json信息如下，其中fulltext对应type：
        ```
        /test419/fulltext/_mapping   POST
        {
            "fulltext": {
                     "_all": {
                    "analyzer": "ik_max_word",
                    "search_analyzer": "ik_max_word",
                    "term_vector": "no",
                    "store": "false"
                },
                "properties": {
                    "content": {
                        "type": "string",
                        "store": "no",
                        "term_vector": "with_positions_offsets",
                        "analyzer": "ik_max_word",
                        "search_analyzer": "ik_max_word",
                        "include_in_all": "true",
                        "boost": 8
                    }
                }
            }
        }
        ```
    * 1.5.3	mysql --> ES 数据同步
        > 新建shell脚本到/usr/es/odbc_es目录下，内容如下：
         
        > 内容如下，其中数据库连接可以修改：
        ```
        #!/bin/sh
        bin=$JDBC_IMPORTER_HOME/bin
        lib=$JDBC_IMPORTER_HOME/lib
        echo '{
        "type":"jdbc",
        "jdbc":{
        "elasticsearch.autodiscover":true,
        "elasticsearch.cluster":"my-app",
        "url":"jdbc:mysql://192.168.1.63:3306/es_test",
        "user":"root",
        "password":"root",
        "sql":"select *, id as _id from es_cc",
        "elasticsearch":{
            "host":"127.0.0.1",
            "port":9300
            },
        "index":"myindex"
        }
        }' | java \
        -cp "${lib}/*" \
        -Dlog4j.configurationFile=${bin}/log4j2.xml \
        org.xbib.tools.Runner \
        org.xbib.tools.JDBCImporter
        ```
        > 运行，如果遇到logs not fount error：	 
        则在shell脚本所在目录即/usr/es/odbc_es/下，新建logs/jdbc.log即可。
### 2 单机多实例
* 2.1 在/usr/es/elasticsearch-2.3.4/config下分别创建ny，ly，trm三个目录。

* 2.2 分别Copy原config目录下的elasticsearch.yml和logging.yml到ny，ly，trm中

* 2.3 修改各文件夹下的elasticsearch.yml文件
    > 示例如下
    
    ```
        index.analysis.analyzer.default.type : ik
        cluster.name: ly_elasticsearch
        node.master: true
        network.host: 0.0.0.0
        http.port: 9201
        transport.tcp.port: 9301
        discovery.zen.ping.unicast.hosts: ["127.0.0.1:9301"]
        node.max_local_storage_nodes: 3
        参数描述
        cluster.name: 实例名称
        node.master: 是否主节点
        http.port: 当前节点的服务端口（注：可根据服务器实际情况设置）
        transport.tcp.port: es集群节点之间的通信端口号。默认9300
        discovery.zen.ping.unicast.hosts: 集群多播时发现其他节点的主机列表， 真实多机集群环境下，这里会是多个主机的IP列表，默认格式“host:port”的数组
        node.max_local_storage_nodes: 本机实例的个数。注：默认情况下，不建议单机启动多个node
    ```
* 2.4 启动服务

    ```./elasticsearch -d -Des.path.conf=/usr/es/elasticsearch-2.3.4/config/ny -p /usr/es/elasticsearch-2.3.4/config/ny.pid```

    > 通过制定配置文件路径的方式来启动多个实例
