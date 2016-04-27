package com.qunar.flight.autofile.config;


import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.qunar.base.qunit.annotation.Property;
import com.qunar.base.qunit.transport.command.ExecuteCommand;
import com.qunar.base.qunit.transport.command.RpcExecuteCommand;
import com.qunar.base.qunit.transport.config.ServiceConfig;
import com.qunar.base.qunit.transport.model.ServiceDesc;

public class RpcServiceConfig extends ServiceConfig {
    public final static String name = "rpc";

    @Property("class")
    private String className;

    @Property
    private String method;

    @Property
    private String url;

    @Property
    private String version;

    @Property
    private String group;

    @Override
    public ExecuteCommand createCommand() {
        ServiceDesc serviceDesc = new ServiceDesc(this.className, this.method, this.url, this.version, this.group);
        return new RpcExecuteCommand(this.id, serviceDesc, this.desc);
    }

    public static StringBuffer result = new StringBuffer();
    public static StringBuffer before = new StringBuffer();
    public static int i = 1;

    public static void main(String[] args) {


//        String className = "com.qunar.ibeplus.api.itf.IAirBookOrderService";
//        String method = "bookOrder";
//        String url = "l-flightqma9.f.beta.cn0.qunar.com:20990/com.qunar.ibeplus.api.itf.IAirBookOrderService?application=test&dubbo=2.3.3";
//        String group = "ibeplus_beta";
//        String version = "1.0.0";
//        ServiceDesc desc = new ServiceDesc(className, method, url, version, group);
        ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<GenericService>();
        referenceConfig.setInterface("com.qunar.ibeplus.api.itf.IAirBookOrderService");
//        referenceConfig.setVersion("1.0.0");
//        referenceConfig.setGroup("ibeplus_beta");
        referenceConfig.setUrl("dubbo://10.90.187.60:20990/com.qunar.ibeplus.api.itf.IAirBookOrderService?accesslog=true&anyhost=true&application=f_ibeplus_provider&dubbo=3.1.7&interface=com.qunar.ibeplus.api.itf.IAirBookOrderService&methods=cancelOrder,bookOrder&organization=flight&owner=pid_team&pid=4592&revision=1.0.8&service.filter=rpcContextFilter&side=provider&threads=500&timeout=30000&timestamp=1459510375028&version=1.0.0");
        referenceConfig.setGeneric(true);
//        List<> methods=new List<>;
//referenceConfig.setMethods(methods);
        ApplicationConfig application = new ApplicationConfig("f_qa_autofile_provider");
        referenceConfig.setApplication(application);
        GenericService genericService = referenceConfig.get();

        Object result = genericService.$invoke("bookOrder", new String[]{"com.qunar.ibeplus.api.pojo.AirBookOrderRequest"}, new Object[]{null});
        System.out.println("+++++"+result.toString());


//        Object service = RpcServiceFactory.getRpcService(desc);
//        System.out.println("+++++"+service.getClass().getName());
//        Method executeMethod = ReflectionUtils.getMethod(desc.getMethod(), desc.getServiceClass());
//        Type[] types = executeMethod.getGenericParameterTypes();
//
//        for (Type type : types) {
//            String parname = type.toString().substring(6);
//            StringUtil.addXMLStartString(result, "var" + i, before);
//            result.append("\n");
//            StringUtil.addHead(result, before);
//            try {
//                Class c = Class.forName(parname);
//                if (!JsonReflect.isBaseDataType(c)) {
//                    JsonReflect f = new JsonReflect(c);
//                    StringUtil.addClassHead(result, c.getName(), before);
//                    f.getSuperClass(c, result, before);
//                    StringUtil.addEnd(result, before);
//                }
//
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            StringUtil.addXMLEndString(result, "var" + i, before, false);
//            i++;
//        }
//        System.out.println("+++" + result);
    }

}
