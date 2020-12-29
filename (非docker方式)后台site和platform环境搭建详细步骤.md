# 项目发布 
##项目分为测试环境(development分支)/生产环境(master分支)
### 配置文件(确认配置文件是否改动,进行修改)
代码中指定的配置文件,切忌乱修改提交
  application-local.properties 本地环境(本地启动)
  application-dev.properties   开发环境(开发启动)
  application-pro.properties   生产环境(生产启动)
###项目 打包启动
app项目  :  服务器 cd /data/app/ 
           开发环境,执行 ./dev_build.sh --进行拉代码和打包 ./platform-shutdown.sh 杀掉进程  ./platform-startup.sh 启动项目
           生产环境,执行 ./pro_build.sh --进行拉代码和打包 ./platform-shutdown.sh 杀掉进程  ./platform-startup.sh 启动项目
           启动日志 /data/app/log/springboot.log  所有日志文件目录 /data/log/platform
           
site项目  : 服务器 cd /data/app/ 
            配置文件 /data/app/site-config
           开发环境,执行 ./dev_build.sh --进行拉代码和打包 ./site-shutdown.sh 杀掉进程  ./site-startup.sh 启动项目
           生产环境,执行 ./pro_build.sh --进行拉代码和打包 ./site-shutdown.sh 杀掉进程  ./site-startup.sh 启动项目
           启动日志 /data/Tomcat/logs
           打包后war包目录 /data/app/deploy/moyu-site/war
           启动后项目 war包解压 在 /data/app/deploy/moyu-site
           
          

ActiveMq使用说明:
1）activemq管控台使用jetty部署,客户端访问用户名 密码,目前默认admin admin
   /conf/jetty-realm.properties
   访问地址 http://127.0.0.1:8161/admin (ip 对应服务器ip)
2）activemq应该设置有安全连接认证机制，只有符合认证的用户才可以发送和接收消息，
   /conf/activemq.xml添加下面配置,放在broker节点里面,
   ﻿<plugins>
   			<simpleAuthenticationPlugin>
   				<users>
   					<authenticationUser username="admin" password="bWYkxVC74QAeRS5z" groups="users,admins"/>
   				</users>
   			</simpleAuthenticationPlugin>
   	</plugins>
   	
   	开启延迟队列：
   	<broker xmlns="http://activemq.apache.org/schema/core" brokerName="localhost" dataDirectory="${activemq.data}" schedulerSupport="true">
   	后面加上 schedulerSupport="true"
   	
   	
   	
   	
 #环境搭建
 1 安装CentOS 7.2 64位 Linux操作系统
 2 执行yum更新如下安装包
 yum -y install gcc perl lua-devel pcre-devel openssl-devel gd-devel gcc-c++ ncurses-devel libaio autoconf lrzsz unzip
 3 更新系统时间  （检查系统时间，如果不准，可用下面命令校准）
 ntpdate cn.pool.ntp.org
 4 创建安装目录、用户
 mkdir -p /htdocs/software
 mkdir -p /data
 useradd www
 5 上传软件包
     118服务器没有对应的目录，搜索，譬如 linux怎么在根目录下创建目录。
 将软件安装包上传到/htdocs/software
 软件可以从79服务器copy到118服务器 /htdocs/software
 进79服务器/htdocs/softwar 执行scp * root@116.62.117.118:/htdocs/software/
 输入520Moyuxls@20
 同理：
 copy对应的jar包，文件夹等
 scp –r /root/.m2 root@116.62.117.118:/root/.m2
 scp –r /htdocs/wwwroot  root@116.62.117.118:/htdocs/wwwroot
 scp –r /data/  root@116.62.117.118:/data
 配置环境jdk，启动服务 mq redis  nginx是否启动ok,若失败
 在118服务器删除 ActiveMQ  redis-5.0.5 nginx es6 
 重新安装软件
 备注es是必须要重新安装的
 6 安装Nginx
 安装LuaJIT
 cd /htdocs/software
 tar zxf LuaJIT-2.1.0-beta2.tar.gz
 cd LuaJIT-2.1.0-beta2
 make && make install
 export LUAJIT_LIB=/usr/local/lib
 export LUAJIT_INC=/usr/local/include/luajit-2.1
 安装Nginx模块ngx_devel_kit、lua-nginx-module、ngx_cache_purge
 cd /htdocs/software
 tar zxf ngx_devel_kit-0.3.0.tar.gz
 mv ngx_devel_kit-0.3.0 /usr/local/ngx_devel_kit
 tar zxf lua-nginx-module-0.10.7.tar.gz
 mv lua-nginx-module-0.10.7 /usr/local/lua-nginx-module
 tar zxf ngx_cache_purge-2.3.tar.gz
 mv ngx_cache_purge-2.3 /usr/local/ngx_cache_purge
 安装Nginx
 	tar zxf nginx-1.10.3.tar.gz
 mv nginx-1.10.3 /data/
 cd /data 对nginx 重新命名 mv nginx-1.10.3 nginx
 cd /data/nginx 执行
 ./configure --prefix=/data/nginx --user=www --group=www --with-file-aio --with-http_ssl_module --with-http_realip_module --with-http_addition_module --with-http_v2_module --with-http_image_filter_module --with-http_gzip_static_module --with-pcre --with-ld-opt="-Wl,-rpath,${LUAJIT_LIB}" --add-module=/usr/local/ngx_cache_purge --add-module=/usr/local/ngx_devel_kit --add-module=/usr/local/lua-nginx-module
 make && make install
 配置文件设置
 cd /htdocs/software
 tar zxf ngx_lua_waf-0.7.2.tar.gz
 mv ngx_lua_waf-0.7.2 /data/nginx/conf/waf
 校验Nginx配置文件 nginx.config 后检验文件正确性
 /data/nginx/sbin/nginx –t
 报错
 2020/03/06 14:18:51 [emerg] 6774#0: open() "/data/nginx/logs/nginx.pid" failed (2: No such file or directory)
 解决办法: cd /data/nginx   mkdir logs
 启动Nginx
 /data/nginx/sbin/nginx
 平滑重启Nginx（修改配置参数后需要执行来让参数生效）
 /data/nginx/sbin/nginx -s reload
 注意：修改nginx.conf
  把79服务器上/data/nginx/conf/nginx.conf 复制到118 上 或者粘贴覆盖。118对应的nginx.conf配置信息，譬如域名是否需要修改等。
 7 安装JDK (1.8)和Tomcat(7)
 安装JDK
 cd /htdocs/software
 tar zxf jdk-8u112-linux-x64.tar.gz
 mv jdk1.8.0_112/ /data/jdk8
 配置环境变量
 vim /etc/profile
 添加以下配置到末尾
 export JAVA_HOME=/data/jdk8
 export PATH=${JAVA_HOME}/bin:${PATH}
 export CLASSPATH=${JAVA_HOME}/lib:${CLASSPATH} 
 执行命令让环境变量生效
 source /etc/profile
 检查JDK版本
 java -version
 安装Tomcat
 	cd /htdocs/software
 tar zxf apache-tomcat-7.0.81.tar.gz
 mv apache-tomcat-7.0.81 /data/app/Tomcat
 需要通用版app时,不同的文件夹路径
 mv apache-tomcat-7.0.81 /data/moyu-common/Tomcat
 8 安装搜索引擎ElasticSearch
 cd /htdocs/software
 tar zxf elasticsearch-6.4.3.tar.gz
 mv elasticsearch-6.4.3/ /data/
 cd /data
 mv elasticsearch-6.4.3 es6
 cd es6/plugins 执行 mkdir ik
 加入ik分词器
 cd /htdocs/software
 cp es-analysis-ik-6.4.3.zip /data/es6/plugins/ik
 在ik文件下 解压 
 cd /data/es6/plugins/ik
 unzip es-analysis-ik-6.4.3.zip
 cd /data/es6/config 打开 elasticsearch.yml 加入
 #如果启用了 HTTP 端口，那么此属性会指定是否允许跨源 REST 请求
 http.cors.enabled: true
 #如果 http.cors.enabled 的值为 true，那么该属性会指定允许 REST 请求来自何处
 http.cors.allow-origin: "*"
 同时修改 network.host: 127.0.0.1
 cluster.name: elasticsearch
 启动搜索引擎
 es6版本后启动方式变了先新建一个用户（出于安全考虑，elasticsearch默认不允许以root账号运行。）
 useradd es
 chown -R es:es /data/es6/
 cd /data/es6
  su es
 /bin/elasticsearch -d
  ElasticSearch涉及的9200和9300端口注意不要直接暴露在外网，服务器防火墙上不要打开端口，如果需要访问，请设置白名单控制访问源
 
 9 安装Redis缓存服务
 	cd /htdocs/software
 	tar zxf redis-5.0.5.tar.gz
 mv redis-5.0.5 /data/redis-5.0.5
 	cd /data/ redis-5.0.5/
 	make && make install
 修改配置文件
 	vim redis.conf  （检查下面两点，如果一样可以不用修改）
      requirepass x!sdfeBre412SD21i6F$Dr
 	daemonize yes
 bind 127.0.0.1 或者 内网IP   * 设置外网IP会被攻击
 启动Redis服务
 redis-server ./redis.conf
 
 10 安装ActiveMQ
 cd /htdocs/software
 tar zxf apache-activemq-5.14.5-bin.tar.gz
 mv apache-activemq-5.14.5 /data/ActiveMQ
 activemq应该设置有安全连接认证机制，只有符合认证的用户才可以发送和接收消息，
    /conf/activemq.xml添加下面配置,放在broker节点里面,
    ﻿<plugins>
    			<simpleAuthenticationPlugin>
    				<users>
    					<authenticationUser username="admin" password="bWYkxVC74QAeRS5z" groups="users,admins"/>
    				</users>
    			</simpleAuthenticationPlugin>
    	</plugins>
 /data/ActiveMQ/bin/activemq start
 11 maven默认安装
 yum -y install maven
 12 git默认安装
 yum -y install git
 
 13 项目部署
 
 ###项目 打包启动
 app项目  :  服务器 cd /data/app/ 
            开发环境,执行 ./dev_build.sh --进行拉代码和打包 ./platform-shutdown.sh 杀掉进程  ./platform-startup.sh 启动项目
            生产环境,执行 ./pro_build.sh --进行拉代码和打包 ./platform-shutdown.sh 杀掉进程  ./platform-startup.sh 启动项目
            启动日志 /data/app/log/springboot.log  所有日志文件目录 /data/log/platform
            
 site项目  : 服务器 cd /data/app/ 
             配置文件 /data/app/site-config
            开发环境,执行 ./dev_build.sh --进行拉代码和打包 ./site-shutdown.sh 杀掉进程  ./site-startup.sh 启动项目
            生产环境,执行 ./pro_build.sh --进行拉代码和打包 ./site-shutdown.sh 杀掉进程  ./site-startup.sh 启动项目
            启动日志 /data/Tomcat/logs
            打包后war包目录 /data/app/deploy/moyu-site/war
            启动后项目 war包解压 在 /data/app/deploy/moyu-site
 
 启动报错
 Document base /data/www/kstore/upload does not exist or is not a readable directory
 79服务器文件重新复制到指定目录/data/www/kstore/upload
 
 备注：git
 ssh-keygen -C ' szc-host001' -t rsa
 cd /root/.ssh/
 cat id_raa.pub 复制到git的ssh添加上。
 
 centos下 yum快速安装maven
