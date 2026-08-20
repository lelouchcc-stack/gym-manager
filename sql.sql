CREATE DATABASE IF NOT EXISTS gym_manage 
    DEFAULT CHARACTER SET utf8mb4 
    COLLATE utf8mb4_general_ci;

USE gym_manage

-- 后台用户表（老板、前台、教练登录）
CREATE TABLE sys_user(
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '用户ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '登录名',
    password VARCHAR(100) NOT NUll COMMENT '密码(BCrypt加密后)',
    nickname VARCHAR(50) COMMENT '昵称',
    phone VARCHAR(20) COMMENT '手机号',
    email VARCHAR(50) COMMENT '邮箱',
    avatar VARCHAR(50) COMMENT '头像',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态:1正常:0停用',
    remark VARCHAR(200) COMMENT '备注',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间'

)ENGINE=InnoDB COMMENT='后台用户表';
-- 角色表
CREATE TABLE sys_role(
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '角色ID',
    role_name VARCHAR(50) NOT NULL COMMENT '角色名',
    role_key VARCHAR(50) NOT NULL COMMENT '角色标识：admin管理员-staff员工-coach顾客',
    sort INT NOT NULL DEFAULT 0  COMMENT '排序',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态:1正常:0停用',
    remark VARCHAR(200) COMMENT '备注',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间'

)ENGINE=InnoDB COMMENT='角色表';
-- 前端菜单表
CREATE TABLE sys_menu(
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '菜单ID',
    parent_id BIGINT COMMENT '父菜单ID,0为根级',
    menu_name VARCHAR(50) NOT NULL COMMENT '菜单名称',
    menu_type CHAR(1) NOT NULL COMMENT '类型：M目录,C菜单,F按钮',
    path VARCHAR(200) COMMENT '路由地址',
    component VARCHAR(255) COMMENT '组件路径',
    perms VARCHAR(100) COMMENT '权限标识:system,user,list',
    icon VARCHAR(100) COMMENT '图标',
    sort INT NULL DEFAULT 0 COMMENT '排序',
    visible TINYINT NOT NULL DEFAULT 1 COMMENT '是否显示：1显示0不显示',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '是否启用1启用0不启用',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)ENGINE=InnoDB COMMENT '菜单表';
-- 角色关系表
CREATE TABLE sys_user_role(
    user_id BIGINT NOT NULL COMMENT '用户ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    PRIMARY KEY (user_id ,role_id)
)ENGINE=InnoDB COMMENT='用户角色关系表';
-- 角色菜单 关系
CREATE TABLE sys_role_menu(
    role_id BIGINT NOT NULL COMMENT '角色ID',
    menu_id BIGINT NOT NULL COMMENT '菜单ID',
    PRIMARY KEY(role_id,menu_id)
)ENGINE=InnoDB COMMENT = '角色菜单表';
-- 日志
CREATE TABLE sys_oper_log(
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '日志ID',
    user_id BIGINT COMMENT '操作人ID',
    username VARCHAR(50) COMMENT '操作人',
    module VARCHAR(50) COMMENT '操作的模块',
    operation VARCHAR(100) COMMENT '操作详情',
    method VARCHAR(200) COMMENT '请求方法',
    params TEXT COMMENT '请求参数',
    ip VARCHAR(50) COMMENT 'IP',
    status TINYINT COMMENT '状态：1成功0失败',
    error_msg TEXT COMMENT '错误信息',
    cost_time BIGINT COMMENT '耗时毫秒',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间'
)ENGINE=InnoDB COMMENT '操作日志表';
-- 会员
CREATE TABLE member(
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '会员ID',
    name VARCHAR(50) NOT NULL COMMENT '姓名',
    phone VARCHAR(50) NOT NUll COMMENT '手机号',
    gender TINYINT COMMENT '1男2女',
    birthday DATE COMMENT '生日',
    source VARCHAR(50)  COMMENT '客源：介绍、到点、线上',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1正常2到期3冻结',
    remark VARCHAR(200) COMMENT '备注',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
)ENGINE=InnoDB COMMENT '会员表';
-- 消费等级卡类型
CREATE TABLE card_type(
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '卡种ID',
    name VARCHAR(50) NOT NULL COMMENT '卡种名：次卡、季卡',
    type TINYINT NOT NUll COMMENT '类型：1次卡，2时限卡',
    total_count INT COMMENT '总次数', 
    duration_days INT NOT NULL COMMENT '有效期天数',
    price DECIMAL(10,2) NOT NULL COMMENT '价格',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1上架0下架',
    remark VARCHAR(200) COMMENT '备注',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'

)ENGINE=InnoDB COMMENT = '卡种表';
-- 会员持有卡情况
CREATE TABLE member_card(
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '会员卡ID',
    member_id BIGINT NOT NULL COMMENT '会员ID',
    card_type_id BIGINT NOT NULL COMMENT '会员卡种ID',
    total_count INT COMMENT '次卡总数',
    used_count INT NOT NULL DEFAULT 0 COMMENT '次卡使用次数',
    start_date DATE NOT NULL COMMENT '开卡日期',
    expire_date DATE NOT NULL COMMENT '到期日期',
    status TINYINT NOT NUll DEFAULT 1 COMMENT '状态：1有效2已用完3已过期',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
)ENGINE=InnoDB COMMENT= '会员卡表';
-- 会员卡流水
CREATE TABLE member_card_log(
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '流水ID',
    member_card_id BIGINT NOT NULL COMMENT '会员卡ID',
    member_id BIGINT NOT NULL COMMENT '会员ID',
    change_type TINYINT NOT NULL COMMENT '开卡类型:1开卡2续卡3扣次4退卡5退次',
    change_count INT NOT NULL COMMENT '变动次数',
    before_count INT COMMENT '变动前次数',
    after_count INT COMMENT '变动后次数',
    remark VARCHAR(200) COMMENT '备注',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
    KEY idx_card (member_card_id),
    KEY idx_member (member_id)
)ENGINE = InnoDB COMMENT = '会员卡流水表';
-- 教练
CREATE TABLE coach(
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '教练ID',
    name VARCHAR(50) NOT NULL COMMENT '姓名',
    phone VARCHAR(50) COMMENT '手机号',
    avatar VARCHAR(50) COMMENT '头像',
    specialty VARCHAR(50) COMMENT '擅长项目:增肌、减脂、康复',
    intro VARCHAR(500) COMMENT '简介',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态1在职0离职',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'

)ENGINE=InnoDB COMMENT='教练人员表';
-- 课程
CREATE TABLE course(
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '课程ID',
    name VARCHAR(50) NOT NULL COMMENT '课程名：私教XXX',
    type TINYINT NOT NULL COMMENT '类型：私教、团练',
    coach_id BIGINT COMMENT '教练ID',
    duration_min INT NOT NULL DEFAULT 60 COMMENT '时长分钟',
    price DECIMAL(10,2) COMMENT '价格',
    cover VARCHAR(255) COMMENT '封面图',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1上架 0 下架',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
)ENGINE=InnoDB COMMENT ='课程表';
-- 排课
CREATE TABLE schedule(
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '排课ID',
    course_id BIGINT NOT NULL COMMENT '课程ID',
    coach_id BIGINT NOT NULL COMMENT '教练ID',
    start_time DATETIME NOT NULL COMMENT '开始时间',
    end_time DATETIME NOT NULl COMMENT '结束时间',
    capacity INT NOT NULL DEFAULT 1 COMMENT '容量多少人',
    booked_count INT NOT NULL DEFAULT 0 COMMENT '已约人数',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态1可约2已满3已取消',
    remark VARCHAR(200) COMMENT '备注',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    KEY idx_course(course_id),
    KEY idx_coach_time(coach_id,start_time)
)ENGINE=InnoDb COMMENT = '排课表';
-- 预约登记
CREATE TABLE booking_record(
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '预约id',
    schedule_id BIGINT NOT NUll COMMENT '课程id',
    member_id BIGINT NOT NUll COMMENT '会员Id',
    card_id BIGINT COMMENT '扣次数的会员卡',
    booking_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '预约时间',
    status TINYINT NOT NULL DEFAULT 1 COMMENT  '状态1已预约2已取消3已完成',
    cancel_time DATETIME COMMENT '取消时间',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    KEY idx_member(member_id),
    KEY idx_schedule(schedule_id),
    UNIQUE KEY uk_schedule_member(schedule_id,member_id) COMMENT '同一人一个时间段只能参加一个'
)ENGINE = InnoDB COMMENT= '预约记录表'