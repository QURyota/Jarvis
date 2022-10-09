package com.ryota;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = DemoApplication.class)
public class CodeGenerator {

    @Test
    public void run() {

        // 1、创建代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 2、全局配置
        GlobalConfig gc = new GlobalConfig();
        // 此处建议写项目/src/main/java源代码的绝对路径
        gc.setOutputDir("/Users/ryota/dev/idea_workspace/Jarvis/demo/src/main/java");
        // 生成注释时的作者
        gc.setAuthor("ryota");
        //生成后是否打开资源管理器
        gc.setOpen(false);
        gc.setFileOverride(false); //重新生成时文件是否覆盖
        gc.setServiceName("%sService");	//去掉Service接口的首字母I



        gc.setIdType(IdType.ASSIGN_UUID); //主键策略
        gc.setDateType(DateType.ONLY_DATE); //定义生成的实体类中日期类型
        gc.setBaseColumnList(true); //自动生成xml文件中的<sql>标签
        gc.setBaseResultMap(true); //自动生成xml文件中的<resultMap>标签
        gc.setSwagger2(true); //开启Swagger2

        mpg.setGlobalConfig(gc);

        // 3、数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
//        dsc.setUrl("jdbc:mysql://10.71.177.82:3306/cnooc-cy?serverTimezone=Asia/Shanghai");
        dsc.setUrl("jdbc:mysql://127.0.0.1:3306/haruharu?serverTimezone=Asia/Shanghai");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("12345678");
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);

        // 4、包配置
        PackageConfig pc = new PackageConfig();
        // 此处要注意：parent为包的名字，在这个包下，创建对应的controller...
        pc.setParent("com.ryota.normal");
        pc.setController("controller");
        pc.setEntity("entity");
        pc.setService("service");
        pc.setMapper("mapper");
        mpg.setPackageInfo(pc);

        // 5、策略配置
        StrategyConfig strategy = new StrategyConfig();
        // 数据库中表的名字，表示要对哪些表进行自动生成controller service、mapper...,逗号隔开
        strategy.setInclude("office_house_apply_information","office_house_apply_master");
        strategy.setNaming(NamingStrategy.underline_to_camel);// 数据库表映射到实体的命名策略,驼峰命名法
        strategy.setColumnNaming(NamingStrategy.underline_to_camel); //数据库表字段映射到实体的命名策略
        strategy.setEntityLombokModel(true); // lombok
        strategy.setRestControllerStyle(true); //restful api风格控制器
        strategy.setControllerMappingHyphenStyle(true); //url中驼峰转连字符

        mpg.setStrategy(strategy);

        // 6、执行
        mpg.execute();
    }
}

