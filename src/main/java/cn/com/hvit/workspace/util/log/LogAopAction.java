package cn.com.hvit.workspace.util.log;

import org.aspectj.lang.annotation.Aspect;

/**
 * Created by Administrator on 2016/11/14.
 */
@Aspect
public class LogAopAction {
//    //注入service，用来将日志信息保存在数据库
//    @Autowired
//    private ILogService logService;
//
//    //配置切入点
//    @Pointcut("execution(* cn.com.hvit.workspace.controller.aopController..*.*(..))")
//    private void controllerAspect(){}
//
//    @Around("controllerAspect()")
//    public Object around(ProceedingJoinPoint pjp) throws Throwable{
//        //常见日志实体对象
//        Ls_Log log = new Ls_Log();
//        //
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        XtUser user = (XtUser) request.getSession().getAttribute("user");
//        if(null != user){
//            String name = user.getLoginname();
//            log.setUserid(BigDecimal.valueOf(Long.parseLong(user.getUserid())));
//        }
//        //获取系统时间
//        String time = new SimpleDateFormat("YYYY-MM-dd HH:mm;ss").format(new Date());
//
//        //获取系统ip
//        CommonCode code = new CommonCode();
//        String clientIP = code.getClientIp(request);
//        log.setClientip(clientIP);
//
//        //方法通知前获取时间，  为什么要记录这个时间呢？当然是用来计算模块执行时间的
//        long start = System.currentTimeMillis();
//        //拦截的实体类  就是当前正在执行的controller
//        Object target = pjp.getTarget();
//        //拦截的方法名称  当前正在执行的方法
//        String methodName = pjp.getSignature().getName();
//        //拦截方法的参数
//        Object[] args = pjp.getArgs();
//        //拦截的参数类型
//        Signature sig = pjp.getSignature();
//        MethodSignature msig = null;
//        if (!(sig instanceof MethodSignature)) {
//            throw new IllegalArgumentException("该注解只能用于方法");
//        }
//        msig = (MethodSignature) sig;
//        Class[] parameterTypes = msig.getMethod().getParameterTypes();
//
//        Object object = null;
//
//        //获取被拦截的方法
//        Method method = null;
//        try{
//            method = target.getClass().getMethod(methodName,parameterTypes);
//        }catch (NoSuchMethodException e1){
//            e1.printStackTrace();
//        }catch (SecurityException e){
//            e.printStackTrace();
//        }
//        if (null != method){
//            if(method.isAnnotationPresent(SystemLog.class)){
//                SystemLog systemLog = method.getAnnotation(SystemLog.class);
//                log.setLogcontent(systemLog.module()+ " " +systemLog.methods());
//                try{
//                    object = pjp.proceed();
//                    //long end = System.currentTimeMillis();
//                    log.setLogtype("success");
//                    //保存数据库
//                    logService.addLog(log);
//
//                }catch (Throwable e){
//                     log.setLogtype("failed");
//                    logService.addLog(log);
//                }
//            }else{  //没有包含注解
//                object = pjp.proceed();
//            }
//        }else { //不需要拦截，直接执行
//            object = pjp.proceed();
//        }
//        return object;
//    }

}
