package com.qunar.flight.autofile.config;


import com.qunar.base.qunit.annotation.Property;
import com.qunar.base.qunit.transport.command.ExecuteCommand;
import com.qunar.base.qunit.transport.command.RpcExecuteCommand;
import com.qunar.base.qunit.transport.config.ServiceConfig;
import com.qunar.base.qunit.transport.model.ServiceDesc;
import com.qunar.base.qunit.transport.rpc.RpcServiceFactory;

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


        String className = "com.qunar.ibeplus.api.itf.IAirBookOrderService";
        String method = "bookOrder";
        String url = "l-flightqma9.f.beta.cn0.qunar.com:20990/com.qunar.ibeplus.api.itf.IAirBookOrderService?application=test&dubbo=2.3.3";
        String group = "ibeplus_beta";
        String version = "1.0.0";
        ServiceDesc desc = new ServiceDesc(className, method, url, version, group);
        Object service = RpcServiceFactory.getRpcService(desc);
        System.out.println(service.getClass().getName());
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
