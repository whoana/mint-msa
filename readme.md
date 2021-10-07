- spring-boot dependency
	https://docs.spring.io/spring-boot/docs/2.4.1/reference/html/appendix-dependency-versions.html#dependency-versions
- reference
	https://docs.spring.io/spring-boot/docs/2.4.1/reference/html/index.html
- howto
	https://docs.spring.io/spring-boot/docs/2.4.1/reference/html/howto.html#howto <--16.4 Create an Executable JAR with Maven , 여기서부터 읽어
- Common Application properties
	https://docs.spring.io/spring-boot/docs/2.4.1/reference/html/appendix-application-properties.html#common-application-properties


- Getting Started

	1. Introducing Spring Boot
		스프링부트 제공 목적(목표)
		Our primary goals are:
			Provide a radically faster and widely accessible getting-started experience for all Spring development.4
			더 신속하고 넓게 스프링개발 경험을 시작할 수 있도록
			Be opinionated out of the box but get out of the way quickly as requirements start to diverge from the defaults.
			기본적으로 의견을 제시하되 기본으로 부터 벗어난 요구사항을 빠르게 대응하기 위해
			Provide a range of non-functional features that are common to large classes of projects (such as embedded servers, security, metrics, health checks, and externalized configuration).
			함수 범위 이상의 많은 클래스들과 프로젝트에 적합한 특성을 제공하기 위해 이를테면  임베디드서버, 시큐러티, 메트릭스, 핼스체크, 외부 설정 등등)
			Absolutely no code generation and no requirement for XML configuration.
			궁국저긍로는 어떠한 코드 생산없이 또 xml 설정 없이
	2. System Requirements
		Spring Boot 2.4.1 은 JAVA 1.8 ~ JAVA15, Spring Framework 5.3.2 이상이 필요하다.
		빌드툴은 Maven 3.3 + , Gradle 6 (6.3 or later). 5.6.x 이 필요하다.
		Servlet Container 는 Tomcat 9.0, Jetty 9.4, Undertow2.0



- mint-front boot 전환
  1) mint-common.jar 중복 Data 객체 정리
  2) mint-database.jar
        XXXMonitorMapper.xml, TrackingMapper.xml

    <resultMap id="TRLogDetailResult" type="TRLogDetail">
        <result property="logKey1"          column="MASTERLOG_ID" />
        <result property="logKey2"          column="MSG_DATETIME" />
        <result property="logKey3"          column="DETAILLOG_ID" />
        <result property="processMode"      column="PROCESS_MODE" />
        <result property="hostId"           column="HOST_ID" />
        <result property="processId"        column="PROCESS_ID" />
        <result property="processTime"      column="PROCESS_TIME" />
        <result property="status"           column="STATUS" />
        <result property="recordCnt"        column="DATA_CNT" />
        <result property="dataSize"         column="DATA_SIZE" />
        <result property="compressYn"       column="COMPRESS_YN" />
        <result property="errorMsg"         column="ERROR_MSG" />
        <result property="detail01"         column="DETAIL01" />
        <!-- <result property="msg"              column="DATA"  ></result> -->  < ---- 처리할 곳
        <result property="msgToByte"        column="DATA"  jdbcType="BLOB" ></result>
        <!--
        <result property="msgToByte"        column="DATA"  javaType="java.lang.byte" jdbcType="BLOB" typeHandler="org.apache.ibatis.type.BlobTypeHandler"></result>
         -->
        <result property="statusNm"         column="STATUSNM" />
        <result property="directoryNm"      column="DIRECTORY_NM" />
        <result property="fileNm"           column="FILE_NM" />
    </resultMap>

   3) Caused by: org.apache.ibatis.builder.BuilderException: Error parsing Mapper XML. The XML location is 'class path resource [pep/per/mint/database/mapper/op/ProblemLedgerMapper.xml]'. Cause: org.apache.ibatis.builder.BuilderException: An invalid property 'dbcType' was found in mapping #{modDate , dbcType=VARCHAR}.  Valid properties are javaType,jdbcType,mode,numericScale,resultMap,typeHandler,jdbcTypeName


    <update id="deleteProblemTemplate" parameterType="ProblemTemplate">
        UPDATE TOP0311
        SET
            DEL_YN              = 'Y',
            MOD_DATE            = #{modDate , dbcType=VARCHAR}, <---------- dbcType 오타 수정
            MOD_USER            = #{modUser , jdbcType=VARCHAR}
        WHERE
            CHANNEL_ID = #{channelId} AND TEMPLATE_ID = #{templateId, jdbcType=NUMERIC}
    </update>

    4)Caused by: org.apache.ibatis.builder.BuilderException: Error parsing Mapper XML. The XML location is 'class path resource [pep/per/mint/database/mapper/im/EngineMonitorMapper.xml]'. Cause: java.lang.IllegalStateException: No typehandler found for property system
    at org.apache.ibatis.builder.xml.XMLMapperBuilder.configurationElement(XMLMapperBuilder.java:123)
    at org.apache.ibatis.builder.xml.XMLMapperBuilder.parse(XMLMapperBuilder.java:95)
    at org.mybatis.spring.SqlSessionFactoryBean.buildSqlSessionFactory(SqlSessionFactoryBean.java:611)
    ... 74 common frames omitted
Caused by: java.lang.IllegalStateException: No typehandler found for property system
    pep.per.mint.database.mapper.im.EngineMonitorMapper.xml
    <resultMap type="QmgrInfo" id="qmgrResultMap">
        <id property="qmgrId"           column="QMGR_ID"/>
        <result property="qmgrNm"       column="QMGR_NM"/>
        <result property="ip"           column="QM_IP"/>
        <result property="port"         column="QM_PORT"/>
        <result property="type"         column="QM_TYPE"/>
        <result property="typeNm"       column="QM_TYPE_NM"/>
        <result property="status"       column="QM_STATUS"/>
        <!-- <result property="system"      column="QM_STATUS"/> --> <----- 요기 수정
        <result property="comments"         column="QM_COMMENTS"/>
        <association property="system" javaType="pep.per.mint.common.data.basic.System">
                <id property="systemId"                 column="SYSTEM_ID"/>
                <result property="systemNm"             column="SYSTEM_NM"/>
                <result property="systemCd"             column="SYSTEM_CD"/>
        </association>

    4) CoreMapper.xml 에 ofType="pep.per.mint.common.data.basic.System" javaType="list"> 추가
        <collection property="systemList" ofType="pep.per.mint.common.data.basic.System" javaType="list">

    5) boot 실행 쉘에 시스템 환경변수 추가
        -DtestUploadPath=/Users/whoana/DEV/workspace-refactoring/mint-msa/upload

    6) mint-front 에 컨트롤러 ba.JobController 가 mint-batch 에 구현된 JobMapper 를 사용하고 있어 임시로 @Controller 주석처리함.

    7) 데이터베이스 설정이 걸리므로  아래 빈을 생성하는 것을 생략하기 위해
    au.AuthorityContoroller 내에 authorityRegisterBatchService 의 어노테이션 @Autowired 주석처리함
    //@Autowired
    AuthorityRegisterBatchService authorityRegisterBatchService;

    <!-- Global Authority Register Batch -->
    <beans:bean id="authorityRegisterBatchService" class="pep.per.mint.database.service.au.AuthorityRegisterBatchService">
        <beans:property name="sqlSessionFactory"  ref="iipSqlSessionFactory"/>
    </beans:bean>

    8) BatchManger 서비스 오토와이어 베제 를 위해 mint-front pom.xml 에서 mint-batch  디펜던시 제거 및 ba.JobController.java 소스 삭제

    9) mint-front pom.xml mint-fetch  디펜던시 제거
    <!--                <dependency>
                    <groupId>pep.per</groupId>
                    <artifactId>mint-fetch</artifactId>
                    <version>${pep.per.mint-version}</version>
                </dependency>
 -->

   10) 웹소켓 에러 --> 미결

    WebsocketConfig.java  주석 처리 setMaxBinaryMessageBufferSize, setMaxTextMessageBufferSize
    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer(WebsocketEnvironments envs){
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        //container.setMaxBinaryMessageBufferSize(envs.maxBinaryMessageBufferSize);
        //container.setMaxTextMessageBufferSize(envs.maxTextMessageBufferSize);
        return container;
    }


java.lang.NoSuchMethodError: org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean.setMaxBinaryMessageBufferSize(I)V
    at pep.per.mint.websocket.WebsocketConfig.createWebSocketContainer(WebsocketConfig.java:267)
    at pep.per.mint.websocket.WebsocketConfig$$EnhancerBySpringCGLIB$$e2a0dbcd.CGLIB$createWebSocketContainer$16(<generated>)
    at pep.per.mint.websocket.WebsocketConfig$$EnhancerBySpringCGLIB$$e2a0dbcd$$FastClassBySpringCGLIB$$c9680c3a.invoke(<generated>)
    at org.springframework.cglib.proxy.MethodProxy.invokeSuper(MethodProxy.java:244)
    at org.springframework.context.annotation.ConfigurationClassEnhancer$BeanMethodInterceptor.intercept(ConfigurationClassEnhancer.java:331)
    at pep.per.mint.websocket.WebsocketConfig$$EnhancerBySpringCGLIB$$e2a0dbcd.createWebSocketContainer(<generated>)
    at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)

    11) 다음 에러로 라애 주석처리함 .

    <!-- <default-servlet-handler/> -->


    에러내용

    2021-09-29 17:30:37.559 ERROR 20306 --- [           main] o.s.boot.SpringApplication               : Application run failed

org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler#0': Initialization of bean failed; nested exception is java.lang.IllegalStateException: Unable to locate the default servlet for serving static content. Please set the 'defaultServletName' property explicitly.


    =======> 여기까지 하면 서비스가 뜨네요;;;;;;;;; 하악하악
