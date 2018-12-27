//package com.rechel.user.web;
//
//
//import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
//import com.google.code.kaptcha.impl.DefaultKaptcha;
//import com.nuts.base.bean.ResultVO;
//import com.nuts.base.core.ResultGenerator;
//import com.nuts.user.service.impl.MailService;
//import com.nuts.user.utils.*;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
//
//import com.nuts.user.service.IUserService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayOutputStream;
//import java.io.UnsupportedEncodingException;
//import java.security.NoSuchAlgorithmException;
//import java.util.List;
//import java.util.Random;
//import java.util.UUID;
//import java.util.concurrent.TimeUnit;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import com.nuts.user.bean.request.UserReqVO;
//import com.nuts.user.bean.response.UserVO;
//
//import javax.imageio.ImageIO;
//import javax.servlet.ServletOutputStream;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import static com.nuts.user.service.impl.SendSMSService.sendSms;
//
///**
// * <p>
// * 用户表 前端控制器
// * </p>
// *
// * @author weinan
// * @since 2018-09-03
// */
//@RestController
//@Slf4j
//@RequestMapping("api/user")
//@Api(value = "user-api", tags = { "用户表相关接口" })
//public class TestController {
//
//    @Autowired
//    private IUserService userService;
//
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
//
//    @Autowired
//    private DefaultKaptcha defaultKaptcha;
//
//    @Autowired
//    private MailService mailService;
//
//    @ApiOperation("分页查询用户表")
//    @GetMapping("user/findPage")
//    public ResultVO getUserPage(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize,
//                                UserReqVO userReqVO) {
//        PageHelper.startPage(pageNum, pageSize);
//        List<UserVO> userVOs = userService.getList(userReqVO);
//        return ResultGenerator.getSuccess(new PageInfo<UserVO>(userVOs));
//    }
//
//    @ApiOperation("查询用户表")
//    @GetMapping("user/find")
//    public ResultVO getUsere(UserReqVO userReqVO) {
//        UserVO userVO = userService.get(userReqVO);
//        return ResultGenerator.getSuccess(userVO);
//    }
//
//    @ApiOperation("添加用户表")
//    @PostMapping("user/create")
//    public ResultVO createUser(@RequestBody UserReqVO userReqVOs) {
//        int count = userService.insert(userReqVOs);
//        return ResultGenerator.getInsertSuccess(count);
//    }
//
//    @ApiOperation("更新用户表")
//    @PutMapping("user/update")
//    public ResultVO updateUser(@RequestBody UserReqVO userReqVOs) {
//        int count = userService.update(userReqVOs);
//        return ResultGenerator.getUpdateSuccess(count);
//    }
//
//    @ApiOperation("批量删除用户表")
//    @DeleteMapping("user/deleteList")
//    public ResultVO deleteUser(@ApiParam(value = "user id集合") @RequestBody int[] ids) {
//        int count = userService.deleteList(ids);
//        return ResultGenerator.getDeleteSuccess(count);
//    }
//
//
//    /**
//     * @param userid     用户名 手机号、email地址
//     * @param password   密码
//     * @param vrifyCode1 短信、email验证码
//     * @param request    从中获取cookie
//     * @return
//     */
//    @ApiOperation("注册")
//    @PostMapping("/register")
//    public com.nuts.user.utils.ResultVO register(@RequestParam("userid") String userid, @RequestParam("password") String password, @RequestParam("vrifyCode1") String vrifyCode1, HttpServletRequest request) {
//
//        //使用cookie中保存的验证码token获取redis中对应的验证码
//        Cookie cookie = CookieUtils.get(request, CookieConstant.TOKEN_VRIFY1);
//
//        if (cookie == null) {
//            return ResultVOUtil.error(1, "验证码超时，请重新发送验证码");
//        }
//
//        String vrifyCode_cookie = cookie.getValue();
//        String vrifyCodeRedis = stringRedisTemplate.opsForValue().get(String.format(RedisConstant.VRIFY_PREFIX1, vrifyCode_cookie));
//        //判断验证码是否正确
//        if (vrifyCode1.isEmpty() || !vrifyCode1.equals(vrifyCodeRedis)) {
//            log.info("--验证码错误或已过期--" + vrifyCodeRedis);
//            return ResultVOUtil.error(1, "验证码错误或已过期");
//        }
//
//        UserVO u = null;
//        UserReqVO userReqVO = new UserReqVO();
//        if (UserUtil.isEmail(userid)) {
//            log.info("--用户使用邮箱地址注册--");
//
//            userReqVO.setEmail(userid);
//            u = userService.get(userReqVO);
//        } else if (UserUtil.isPhoneNumberValid(userid)) {
//            userReqVO.setPhone(userid);
//            u = userService.get(userReqVO);
//        } else {
//            log.info("--用户名不合法--");
//            return ResultVOUtil.error(1, "用户名不合法");
//
//        }
//
//
//        if (u != null) {
//            log.info("用户名已经被占用");
//
//            return ResultVOUtil.error(1, "用户名已经被占用");
//        } else {
//            try {
//                userReqVO.setPassword(UserUtil.EncoderByMd5(password));
//            } catch (NoSuchAlgorithmException e) {
//                e.printStackTrace();
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//        }
//
//        userService.insert(userReqVO);
//        //TODO：发邮件、短信 通知用户注册成功
//
//
//        return ResultVOUtil.success();
//    }
//
//
//    /**
//     * @param userid      用户名 手机号、email地址
//     * @param userPasswor 密码
//     * @param vrifyCode   图形验证码
//     * @param request     取cookie
//     * @param response    存cookie
//     * @return
//     */
//    @ApiOperation("登录")
//    @GetMapping("/login")
//    public com.nuts.user.utils.ResultVO login(@RequestParam("userid") String userid, @RequestParam("userPassword") String userPasswor, @RequestParam("vrifyCode") String vrifyCode, HttpServletRequest request, HttpServletResponse response) {
//        log.info("--登录开始----手机号：" + userid + "-密码：" + userPasswor);
//        UserVO user = null;
//        UserReqVO userReqVO = new UserReqVO();
//        String userPassword = "";
//        try {
//            //密码加密
//            userPassword = UserUtil.EncoderByMd5(userPasswor);
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        //使用cookie中保存的验证码token获取redis中对应的验证码
//        Cookie cookie = CookieUtils.get(request, CookieConstant.TOKEN_VRIFY);
//        String vrifyCode_cookie = cookie.getValue();
//        String vrifyCodeRedis = stringRedisTemplate.opsForValue().get(String.format(RedisConstant.VRIFY_PREFIX, vrifyCode_cookie));
//        //判断验证码是否正确
//        if (vrifyCode.isEmpty() || !vrifyCode.equals(vrifyCodeRedis)) {
//            log.info("--验证码错误或已过期--" + vrifyCodeRedis);
//            return ResultVOUtil.error(1, "验证码错误或已过期");
//        }
//        //根据用户名、密码获取用户，
//        if (UserUtil.isPhoneNumberValid(userid)) {
//            log.info("--用户使用手机号登录--");
//            userReqVO.setPhone(userid);
//            userReqVO.setPassword(userPassword);
//            user = userService.get(userReqVO);
//        } else if (UserUtil.isEmail(userid)) {
//            log.info("--用户使用邮箱登录--");
//            userReqVO.setEmail(userid);
//            userReqVO.setPassword(userPassword);
//            user = userService.get(userReqVO);
//        } else {
//            log.info("--用户名不合法--");
//            return ResultVOUtil.error(1, "用户名不合法");
//        }
//        if (user == null) {
//            log.info("--用户名密码不正确--");
//            return ResultVOUtil.error(1, "用户名密码不正确");
//        }
//
//        //生成token
//        String token = UUID.randomUUID().toString();
//        //超时时间
//        Integer expire = RedisConstant.EXPIRE;
//        //设置到redis
//        stringRedisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token), user.getId() + "", expire, TimeUnit.SECONDS);
//        //设置token到cookie
//        CookieUtils.set(response, CookieConstant.TOKEN_LOGIN, token, expire);
//        //return new ModelAndView("redirect:"+"/user/main");
//        return ResultVOUtil.success(user);
//
//
//    }
//
//
//    /**
//     * 登出
//     *
//     * @param request
//     * @param response
//     * @return
//     */
//    @ApiOperation("用户退出登录")
//    @GetMapping("/logout")
//    public com.nuts.user.utils.ResultVO logout(HttpServletRequest request, HttpServletResponse response) {
//
//        Cookie cookie = CookieUtils.get(request, CookieConstant.TOKEN_LOGIN);
//        if (cookie != null) {
//            //清除token
//            stringRedisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
//            //清除cookie
//            CookieUtils.set(response, CookieConstant.TOKEN_LOGIN, null, 0);
//        }
//
//        return ResultVOUtil.success();
//
//    }
//
//
//    /**
//     * 生成图形验证码
//     *
//     * @param httpServletRequest
//     * @param httpServletResponse
//     * @throws Exception
//     */
//    @ApiOperation("生成验证码")
//    @GetMapping("/sendImageCode")
//    public void defaultKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
//        log.info("进入defaultKaptcha方法");
//        byte[] captchaChallengeAsJpeg = null;
//        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
//        try {
//            //生产验证码字符串并保存到token中
//            String createText = defaultKaptcha.createText();
//            log.info("生成的验证码是：" + createText);
//            Cookie cookie = CookieUtils.get(httpServletRequest, CookieConstant.TOKEN_VRIFY);
//            if (cookie != null) {
//                //清除token
//                stringRedisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.VRIFY_PREFIX, cookie.getValue()));
//
//            }
//            //生成token
//            String token = UUID.randomUUID().toString();
//            //超时时间
//            Integer expire = RedisConstant.EXPIRE_VRIFY;
//            //设置到redis
//            stringRedisTemplate.opsForValue().set(String.format(RedisConstant.VRIFY_PREFIX, token), createText + "", expire, TimeUnit.SECONDS);
//            //设置token到cookie
//            CookieUtils.set(httpServletResponse, CookieConstant.TOKEN_VRIFY, token, expire);
//
//            //使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
//            BufferedImage challenge = defaultKaptcha.createImage(createText);
//            ImageIO.write(challenge, "jpg", jpegOutputStream);
//        } catch (IllegalArgumentException e) {
//            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
//            return;
//        }
//
//        //定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
//        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
//        httpServletResponse.setHeader("Cache-Control", "no-store");
//        httpServletResponse.setHeader("Pragma", "no-cache");
//        httpServletResponse.setDateHeader("Expires", 0);
//        httpServletResponse.setContentType("image/jpeg");
//        ServletOutputStream responseOutputStream = httpServletResponse.getOutputStream();
//        responseOutputStream.write(captchaChallengeAsJpeg);
//        responseOutputStream.flush();
//        responseOutputStream.close();
//    }
//
//
//    /**
//     * 根据用户名 图形验证码 判断验证码是否正确
//     *
//     * @param userid
//     * @param vrifyCode 图形验证码
//     * @param request
//     * @return
//     * @throws Exception
//     */
//    @ApiOperation("验证图形验证码")
//    @GetMapping("/checkUseridAndCode")
//    public com.nuts.user.utils.ResultVO checkVcode(@RequestParam("userid") String userid, @RequestParam("vrifyCode") String vrifyCode, HttpServletRequest request) throws Exception {
//
//        UserReqVO userReqVO = new UserReqVO();
//        if (UserUtil.isPhoneNumberValid(userid)) {
//            userReqVO.setPhone(userid);
//        } else if (UserUtil.isEmail(userid)) {
//            userReqVO.setEmail(userid);
//        } else {
//
//        }
//
//        UserVO userVO = userService.get(userReqVO);
//
//        //使用cookie中保存的验证码token获取redis中对应的验证码
//        Cookie cookie = CookieUtils.get(request, CookieConstant.TOKEN_VRIFY);
//        String vrifyCode_cookie = cookie.getValue();
//        String vrifyCodeRedis = stringRedisTemplate.opsForValue().get(String.format(RedisConstant.VRIFY_PREFIX, vrifyCode_cookie));
//        //判断验证码是否正确
//        if (vrifyCode.isEmpty() || !vrifyCode.equals(vrifyCodeRedis)) {
//            log.info("验证失败");
//            return ResultVOUtil.error(1, "验证失败");
//        } else {
//            log.info("验证成功");
//            return ResultVOUtil.success();
//        }
//    }
//
//
//    /**
//     * 发送验证码到email或短信
//     *
//     * @param
//     * @param httpServletRequest
//     * @param httpServletResponse
//     * @return
//     * @throws Exception
//     */
//    @GetMapping("/sendCode")
//    @ApiOperation("发送验证码到email或短信")
//    public com.nuts.user.utils.ResultVO sendCode(@RequestParam("userid") String userid, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
//
//        int isPhone = 0;
//        if (UserUtil.isPhoneNumberValid(userid)) {
//            isPhone = 0;
//        } else if (UserUtil.isEmail(userid)) {
//            isPhone = 1;
//        }
//
//        String address = userid;
//        log.info("发送地址：" + address);
//
//        Random random = new Random();
//        String createText = "";
//        for (int i = 0; i < 6; i++) {
//            createText += random.nextInt(10);
//        }
//
//
//        //生产验证码字符串并保存到token中
//        log.info("生成的验证码是：" + createText);
//        Cookie cookie = CookieUtils.get(httpServletRequest, CookieConstant.TOKEN_VRIFY1);
//        if (cookie != null) {
//            //清除token
//            stringRedisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.VRIFY_PREFIX1, cookie.getValue()));
//
//        }
//        //生成token
//        String token = UUID.randomUUID().toString();
//        //超时时间
//        Integer expire = RedisConstant.EXPIRE_VRIFY;
//        //设置到redis
//        stringRedisTemplate.opsForValue().set(String.format(RedisConstant.VRIFY_PREFIX1, token), createText, expire, TimeUnit.SECONDS);
//        //设置token到cookie
//        CookieUtils.set(httpServletResponse, CookieConstant.TOKEN_VRIFY1, token, expire);
//
//        String content = "您的验证码是：" + createText;
//
//        log.info(content);
//        if (isPhone == 0) {
//            //发送短信验证码
//            SendSmsResponse response = sendSms(userid, createText);
//        } else {
//            mailService.sendSimpleMail(address, "邮箱注册验证码", content);
//
//        }
//
//        return ResultVOUtil.success();
//
//    }
//
//
//    /**
//     * 根据用户名（手机号、email地址）、验证码 判断验证码是否正确
//     *
//     * @param vrifyCode1
//     * @param userid
//     * @param request
//     * @return
//     * @throws Exception
//     */
//    @ApiOperation("验证手机email验证码")
//    @GetMapping("/verifCode")
//    public com.nuts.user.utils.ResultVO verifCode(@RequestParam("vrifyCode1") String vrifyCode1, @RequestParam("userid") String userid, HttpServletRequest request) throws Exception {
//
//        log.info("验证码：" + vrifyCode1);
//
//        Cookie cookie = CookieUtils.get(request, CookieConstant.TOKEN_VRIFY1);
//        if (cookie == null) {
//            return ResultVOUtil.error(1, "验证码超时，请重新发送");
//        }
//        String vrifyCode_cookie = cookie.getValue();
//        String vrifyCodeRedis = stringRedisTemplate.opsForValue().get(String.format(RedisConstant.VRIFY_PREFIX1, vrifyCode_cookie));
//        //判断验证码是否正确
//        if (vrifyCode1.isEmpty() || !vrifyCode1.equals(vrifyCodeRedis)) {
//            log.info("验证失败");
//            return ResultVOUtil.error(1, "验证失败");
//        } else {
//            log.info("验证成功");
//            return ResultVOUtil.success();
//        }
//    }
//
//
//    /**
//     * 根据用户名（手机号、email地址）更新用户信息
//     *
//     * @param
//     * @return
//     */
//    @ApiOperation("修改用户信息")
//    @PostMapping("/updateUser")
//    public com.nuts.user.utils.ResultVO updateUser(@RequestParam("userid") String userid, @RequestParam("password") String password, @RequestParam("vrifyCode1") String vrifyCode1, HttpServletRequest request) {
//
//        //使用cookie中保存的验证码token获取redis中对应的验证码
//        Cookie cookie = CookieUtils.get(request, CookieConstant.TOKEN_VRIFY1);
//        String vrifyCode_cookie = cookie.getValue();
//        String vrifyCodeRedis = stringRedisTemplate.opsForValue().get(String.format(RedisConstant.VRIFY_PREFIX1, vrifyCode_cookie));
//        //判断验证码是否正确
//        if (vrifyCode1.isEmpty() || !vrifyCode1.equals(vrifyCodeRedis)) {
//            log.info("--验证码错误或已过期--" + vrifyCodeRedis);
//            return ResultVOUtil.error(1, "验证码错误或已过期");
//        }
//
//        UserReqVO userReqVO = new UserReqVO();
//
//        if (UserUtil.isPhoneNumberValid(userid)) {
//            log.info("--用户使用手机号--");
//            userReqVO.setPhone(userid);
//        } else if (UserUtil.isEmail(userid)) {
//            log.info("--用户使用email--");
//            userReqVO.setEmail(userid);
//        } else {
//            log.info("--用户名不合法--");
//            return ResultVOUtil.error(1, "用户名不合法");
//        }
//
//        UserVO userVO1 = userService.get(userReqVO);
//        userReqVO.setId(userVO1.getId());
//
//        try {
//            userReqVO.setPassword(UserUtil.EncoderByMd5(password));
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//
//        //TODO：发邮件、短信 通知用户更改密码成功
//
//        userService.update(userReqVO);
//        return ResultVOUtil.success();
//    }
//
//}
