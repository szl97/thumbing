package com.thumbing.shared.constants;

/**
 * @author Stan Sai
 * @date 2020-06-28
 */
public class EntityConstants {
    //主键
    public final static String ID = "id";

    //创建人id
    public final static String CREATE_ID = "create_id";

    //创建时间
    public final static String CREATE_TIME = "create_time";

    //修改人id
    public final static String LAST_MODIFY_ID = "last_modify_id";

    //最后修改时间
    public final static String LAST_MODIFY_TIME = "last_modify_time";

    //版本号
    public final static String VERSION = "version";

    //逻辑删除
    public final static String DR = "dr";


    //创建人id
    public final static String CREATE_ID_PROPERTY = "createId";

    //创建时间
    public final static String CREATE_TIME_PROPERTY = "createTime";

    //修改人id
    public final static String LAST_MODIFY_ID_PROPERTY = "lastModifyId";

    //最后修改时间
    public final static String LAST_MODIFY_TIME_PROPERTY = "lastModifyTime";

    public final static String TENANT_ID_PROPERTY = "tenantId";

    /**
     * 逻辑删除得值
     */
    public final static int LOGIC_DELETE_VALUE = 1;
    public final static int LOGIC_UN_DELETE_VALUE = 0;
}
