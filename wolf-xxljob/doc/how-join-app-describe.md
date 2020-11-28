#请求地址
http://127.0.0.1:9001/xxl-admin/jobgroup
备注：ip是对应机器的外网地址
#其他应用接入说明
##jar包接入细节,有问题联系@hong
a.在应用中新建文件夹lib
b.再wq-shceduler项目目录/xxl/doc/jar下复制 xxl-core-1.0.0.jar
  到对应的task相关模块 对应的lib下。
c.pom.xml中加入下面jar包依赖

        <dependency>
  			<groupId>com.wanqin.scheduler</groupId>
  			<artifactId>xxl-core</artifactId>
  			<version>1.0.0</version>
  			<scope>system</scope>
  			<systemPath>${project.basedir}/src/main/webapp/WEB-INF/lib/xxl-core-1.0.0.jar</systemPath>
  		</dependency>
  
  		<dependency>
  			<groupId>com.xuxueli</groupId>
  			<artifactId>xxl-rpc-core</artifactId>
  			<version>1.2.1</version>
  		</dependency>
  
  		<dependency>
  			<groupId>org.codehaus.groovy</groupId>
  			<artifactId>groovy</artifactId>
  			<version>2.5.3</version>
  		</dependency>
  	
  	备注：lib对应的目录,根据需要进行修改。
  	
d.各个应用中xx.properties文件新增
  
  xxl.job.admin.addresses=http://127.0.0.1:9001/xxl-admin
  
  xxl.job.executor.appname=itemApi-job-default
  
  xxl.job.executor.ip=
  
  xxl.job.executor.port=9002
  
  备注：admin和执行器地址根据需要修改,执行器IP默认为空表示自动获取IP，多网卡时可手动设置指定IP,逗号分隔。
  
e.修改定时任务类
  先继承 IJobHandler,再在类的上面定义对应的@JobHandler(value="xxxx")
  
  样例如下:
  @JobHandler(value="libSelectionSaleCount")
  @Component
  @Slf4j
  public class LibSelectionSaleCountJobHandler  extends IJobHandler {
  
  
  同时加入定时任务监控的类,下面类的包名,即定时任务对应的task包名
  
  
      @Configuration
      @ComponentScan(basePackages = "com.wangqin.globalshop.channel.task")
      public class XxlJobConfig {
      
      private Logger logger = LoggerFactory.getLogger(XxlJobConfig.class);
  
      @Value("${xxl.job.admin.addresses}")
      private String adminAddresses;
  
      @Value("${xxl.job.executor.appname}")
      private String appName;
  
      @Value("${xxl.job.executor.ip}")
      private String ip;
  
      @Value("${xxl.job.executor.port}")
      private int port;
  
      @Value("${xxl.job.accessToken}")
      private String accessToken;
  
      @Value("${xxl.job.executor.logpath}")
      private String logPath;
  
      @Value("${xxl.job.executor.logretentiondays}")
      private int logRetentionDays;
  
  
      @Bean(initMethod = "start", destroyMethod = "destroy")
      public XxlJobSpringExecutor xxlJobExecutor() {
          logger.info(">>>>>>>>>>> xxl-job config init.");
          XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
          xxlJobSpringExecutor.setAdminAddresses(adminAddresses);
          xxlJobSpringExecutor.setAppName(appName);
          xxlJobSpringExecutor.setIp(ip);
          xxlJobSpringExecutor.setPort(port);
          xxlJobSpringExecutor.setAccessToken(accessToken);
          xxlJobSpringExecutor.setLogPath(logPath);
          xxlJobSpringExecutor.setLogRetentionDays(logRetentionDays);
  
          return xxlJobSpringExecutor;
          }
      }
  
  
f.对接入定时任务的应用(如item)打包,部署。
  在浏览器输入对应的admin地址,新建执行器,对应properties文件中的appname。
  可以监控到应用的ip信息。
  在任务管理中添加任务,譬如JobHandler注解对应的libSelectionSaleCount,可以完成对此定时任务的启动、暂停、恢复等信息配置。