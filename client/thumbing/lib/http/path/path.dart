
class HttpPath {
  /*
   * auth-server
   * */
  //post
  static String logIn = "/api/auth/login";
  //get userName
  static String checkAuthorization = "/api/auth/authorization";
  //get phoneNum
  static String registerSms = "/api/auth/account/registerSms";
  //post
  static String checkUnique = "/api/auth/account/checkUnique";
  //post
  static String register = "/api/auth/account/register";
  //get phoneNum
  static String changePasswordSms = "/api/auth/account/changerSms";
  //patch
  static String changePassword = "/api/auth/account/changePassword";

  /*
   * user-management
   * */
  //get
  static String personalConfiguration = "/api/user/personalConfiguration";
  //删除 delete 修改 post 获取 get
  static String personal = "/api/user/personal";
  //获取列表 get 删除 delete
  static String relation = "/api/user/relation";
  //发生申请 put
  static String applyRelation = "/api/user/relation/apply";
  //获取申请 get
  static String applyInfoRelation = "/api/user/relation/applyInfo";
  //处理好友请求 post
  static String handleRelation = "/api/user/relation/handle";

  static String backList = "/api/user/blackList";

  /*
  * content-server
  * */
  //article
  //get
  static String fetchArticles = "/api/content/article/fetch";
  //get
  static String getArticleContent = "/api/content/article/content";
  //get
  static String getArticleDetails = "/api/content/article/details";
  //get
  static String getMyArticle = "/api/content/article/mine";
  //post
  static String publishArticle = "/api/content/article/publish";
  //patch
  static String thumbArticle = "/api/content/article/thumb";
  //put
  static String updateArticle = "/api/content/article/update";
  //delete
  static String deleteArticle = "/api/content/article/delete";
  //moments
  //get
  static String fetchMoments = "/api/content/moments/fetch";
  //get
  static String getMomentsDetails = "/api/content/moments/details";
  //get
  static String getMyMoments = "/api/content/moments/mine";
  //post
  static String publishMoments = "/api/content/moments/publish";
  //patch
  static String thumbMoments = "/api/content/moments/thumb";
  //put
  static String updateMoments = "/api/content/moments/update";
  //delete
  static String deleteMoments = "/api/content/moments/delete";
  //roast
  //get
  static String fetchRoasts = "/api/content/roast/fetch";
  //get
  static String getMyRoast = "/api/content/roast/mine";
  //post
  static String publishRoast = "/api/content/roast/publish";
  //patch
  static String thumbRoast = "/api/content/roast/thumb";
  //delete
  static String deleteRoast = "/api/content/roast/delete";
  //comment
  //get
  static String fetchComments = "/api/content/comment/fetch";
  //post
  static String publishComment = "/api/content/comment/publish";
  //patch
  static String thumbComment = "/api/content/comment/thumb";
  //delete
  static String deleteComment = "/api/content/comment/delete";
  //search
  //get
  static String searchContent = "/api/content/search";
}