
class Path{
  /*
   * auth-server
   * */
  static String logIn = "/api/auth/login";
  static String chetAuthorization = "/api/auth/authorization";
  static String registerSms = "/api/auth/account/registerSms";
  static String checkUnique = "/api/auth/account/checkUnique";
  static String register = "/api/auth/account/register";
  static String changePasswordSms = "/api/auth/account/changerSms";
  static String changePassword = "/api/auth/account/changePassword";
 /*
 * user-management
 * */
 static String personalConfiguration = "/api/user/personalConfiguration";
 static String personal = "/api/user/personal";
 static String relation = "/api/user/relation";
 static String applyRelation = "/api/user/relation/apply";
 static String applyInfoRelation = "/api/user/relation/applyInfo";
 static String handleRelation = "/api/user/relation/handle";
}