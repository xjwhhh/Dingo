package service.bean;

import dao.UserDao;
import model.Coupon;
import model.ResultMessage;
import model.User;
import model.VIPLevel;
import net.sf.ezmorph.bean.BeanMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.UserManageService;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Service
public class UserManageServiceBean implements UserManageService {

    private static final int firstCouponCost = 100;
    private static final int secondCouponCost = 500;
    private static final int thirdCouponCost = 1000;
    private static final String firstCouponDesc = "满100减10";
    private static final String secondCouponDesc = "满1000减100";
    private static final String thirdCouponDesc = "满2000减200";

    @Autowired
    UserDao userDao;

    public ResultMessage register(String account, String password) {
        User testUser = userDao.find(account, password);
        if (testUser.getId() != -1) {
            return ResultMessage.FAIL;
        }
        User user = new User();
        user.setAccount(account);
        user.setPassword(password);
        user.setEmailAddress(null);
        user.setConfirmed(false);
        user.setName(null);
        user.setLevel(VIPLevel.COMMON.toString());
        user.setTotalIntegral(0);
        user.setCurrentIntegral(0);
        user.setCancelled(false);
        return userDao.save(user);
    }

    public User login(String account, String password) {
        User user = userDao.find(account, password);
        if (user.isCancelled()) {
            User newUser = new User();
            newUser.setId(-2);
            return newUser;
        } else {
            return user;
        }
    }

    public User getUserById(int userId) {
        return userDao.findById(userId);
    }

    public ResultMessage updateUserInfo(String userJson) {
        System.out.println(userJson);
        JSONObject jsonObject = JSONObject.fromObject(userJson);
        User user = (User) JSONObject.toBean(jsonObject, User.class);
        System.out.println(JSONArray.fromObject(user).toString());
        return userDao.update(user);
    }

    public ResultMessage emailConfirmation(int userId, String emailAddress) {

        if (userDao.findByEmail(emailAddress).getId() == -1||userDao.findByEmail(emailAddress).getId() ==userId) {

            // 发件人的 邮箱 和 密码（替换为自己的邮箱和密码）
            // PS: 某些邮箱服务器为了增加邮箱本身密码的安全性，给 SMTP 客户端设置了独立密码（有的邮箱称为“授权码”）,
            //     对于开启了独立密码的邮箱, 这里的邮箱密码必需使用这个独立密码（授权码）。
            String myEmailAccount = "xjwhhh233@163.com";
            String myEmailPassword = "xjw123456";

            // 发件人邮箱的 SMTP 服务器地址, 必须准确, 不同邮件服务器地址不同, 一般(只是一般, 绝非绝对)格式为: smtp.xxx.com
            // 网易163邮箱的 SMTP 服务器地址为: smtp.163.com
            String myEmailSMTPHost = "smtp.163.com";

            // 1. 创建参数配置, 用于连接邮件服务器的参数配置
            Properties props = new Properties();                    // 参数配置
            props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
            props.setProperty("mail.smtp.host", myEmailSMTPHost);   // 发件人的邮箱的 SMTP 服务器地址
            props.setProperty("mail.smtp.auth", "true");            // 需要请求认证

            // PS: 某些邮箱服务器要求 SMTP 连接需要使用 SSL 安全认证 (为了提高安全性, 邮箱支持SSL连接, 也可以自己开启),
            //     如果无法连接邮件服务器, 仔细查看控制台打印的 log, 如果有有类似 “连接失败, 要求 SSL 安全连接” 等错误,
            //     打开下面 /* ... */ 之间的注释代码, 开启 SSL 安全连接。
        /*
        // SMTP 服务器的端口 (非 SSL 连接的端口一般默认为 25, 可以不添加, 如果开启了 SSL 连接,
        //                  需要改为对应邮箱的 SMTP 服务器的端口, 具体可查看对应邮箱服务的帮助,
        //                  QQ邮箱的SMTP(SLL)端口为465或587, 其他邮箱自行去查看)
        final String smtpPort = "465";
        props.setProperty("mail.smtp.port", smtpPort);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", smtpPort);
        */

            // 2. 根据配置创建会话对象, 用于和邮件服务器交互
            Session session = Session.getInstance(props);
            session.setDebug(true);                                 // 设置为debug模式, 可以查看详细的发送 log

            // 3. 创建一封邮件
            try {
                MimeMessage message = createMimeMessage(session, myEmailAccount, emailAddress);

                // 4. 根据 Session 获取邮件传输对象
                Transport transport = session.getTransport();

                // 5. 使用 邮箱账号 和 密码 连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 否则报错
                //
                //    PS_01: 成败的判断关键在此一句, 如果连接服务器失败, 都会在控制台输出相应失败原因的 log,
                //           仔细查看失败原因, 有些邮箱服务器会返回错误码或查看错误类型的链接, 根据给出的错误
                //           类型到对应邮件服务器的帮助网站上查看具体失败原因。
                //
                //    PS_02: 连接失败的原因通常为以下几点, 仔细检查代码:
                //           (1) 邮箱没有开启 SMTP 服务;
                //           (2) 邮箱密码错误, 例如某些邮箱开启了独立密码;
                //           (3) 邮箱服务器要求必须要使用 SSL 安全连接;
                //           (4) 请求过于频繁或其他原因, 被邮件服务器拒绝服务;
                //           (5) 如果以上几点都确定无误, 到邮件服务器网站查找帮助。
                //
                //    PS_03: 仔细看log, 认真看log, 看懂log, 错误原因都在log已说明。
                transport.connect(myEmailAccount, myEmailPassword);

                // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
                transport.sendMessage(message, message.getAllRecipients());

                // 7. 关闭连接
                transport.close();

            } catch (NoSuchProviderException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            User user = userDao.findById(userId);
            user.setEmailAddress(emailAddress);
            user.setConfirmed(false);
            userDao.update(user);
            return ResultMessage.SUCCESS;
        } else {
            return ResultMessage.FAIL;
        }
    }

    /**
     * 创建一封只包含文本的简单邮件
     *
     * @param session     和服务器交互的会话
     * @param sendMail    发件人邮箱
     * @param receiveMail 收件人邮箱
     * @return
     * @throws Exception
     */
    public MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail) throws Exception {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);

        // 2. From: 发件人（昵称有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改昵称）
        message.setFrom(new InternetAddress(sendMail, "Dingo", "UTF-8"));

        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "尊敬的用户", "UTF-8"));

        // 4. Subject: 邮件主题（标题有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改标题）
        message.setSubject("邮箱认证", "UTF-8");

        // 5. Content: 邮件正文（可以使用html标签）（内容有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改发送内容）
        message.setContent("<html><head></head><body><h1>尊敬的用户您好，这是一封来自Dingo的邮箱验证邮件，请点击链接进行邮箱验证</h1><h3><a href='http://localhost:8080/user/emailReConfirmation?emailAddress="+receiveMail+"'>请点击此处</a></h3><h1>如果不是您发起的验证请求，请忽略本邮件</h1></body></html>", "text/html;charset=UTF-8");

        // 6. 设置发件时间
        message.setSentDate(new Date());

        // 7. 保存设置
        message.saveChanges();

        return message;
    }

    public ResultMessage emailReConfirmation(String emailAddress) {
        User user = userDao.findByEmail(emailAddress);
        user.setEmailAddress(emailAddress);
        user.setConfirmed(true);
        return userDao.update(user);
    }

    public List<User> getAllUsers() {
        return userDao.findUserList();
    }

    public ResultMessage cancel(int userId) {
        User user = userDao.findById(userId);
        user.setCancelled(true);
        return userDao.update(user);
    }

    public ResultMessage exchangeCoupon(int userId, int couponType,int couponNumber) {
        User user = userDao.findById(userId);
        int cost = 0;
        String description = "";
        switch (couponType) {
            case 1:
                cost = firstCouponCost*couponNumber;
                description = firstCouponDesc;
                break;
            case 2:
                cost = secondCouponCost*couponNumber;
                description = secondCouponDesc;
                break;
            case 3:
                cost = thirdCouponCost*couponNumber;
                description = thirdCouponDesc;
                break;
        }
        if (user.getCurrentIntegral() >= cost) {
            user.setCurrentIntegral(user.getCurrentIntegral() - cost);
            for(int i=0;i<couponNumber;i++) {
                Coupon coupon = new Coupon();
                coupon.setUser(user);
                coupon.setType(couponType);
                coupon.setDescription(description);
                userDao.save(coupon);
            }
            userDao.update(user);
            return ResultMessage.SUCCESS;
        } else {
            return ResultMessage.FAIL;
        }
    }

    public static void main(String[] args){
        UserManageServiceBean userManageServiceBean=new UserManageServiceBean();
        userManageServiceBean.emailConfirmation(1,"920054996@qq.com");

    }
}
