create table if not exists "user"(
    uuid char(36) primary key not null,
    username varchar(50) not null unique,
    password_hash TEXT,
    created_at DATETIME not null DEFAULT CURRENT_TIMESTAMP
);
create table if not exists "access_token"(
    uuid char(36) primary key not null,
    name varchar(50) not null,
    hash char(64) not null,
    user_uuid char(36) not null,
    created_at DATETIME not null DEFAULT CURRENT_TIMESTAMP,
    expires_at DATETIME,
    active int not null check (active in(0,1)) default 1,
    foreign key (user_uuid) references user(uuid)
    
);
create table if not exists "server"(
    uuid char(36) primary key not null,
    name varchar(50) not null unique,
    type varchar(20) not null,
    created_at DATETIME not null DEFAULT CURRENT_TIMESTAMP
);
create table if not exists "role"(
    uuid char(36) primary key not null,
    name varchar(50) not null unique,
    system_role int not null check (system_role in(0,1)) default 0,
    created_at DATETIME not null DEFAULT CURRENT_TIMESTAMP,
    can_start int not null check (can_start in(0,1)) default 0,
    can_control_state int not null check (can_control_state in(0,1)) default 0,
    can_modify int not null check (can_modify in(0,1)) default 0,
    can_access_files int not null check (can_access_files in(0,1)) default 0,
    can_see_console int not null check (can_see_console in(0,1)) default 0,
    can_use_console int not null check (can_use_console in(0,1)) default 0,
    can_see_history int not null check (can_see_history in(0,1)) default 0,
    is_administrator int not null check (is_administrator in(0,1)) default 0
);
create table if not exists "user_server_role"(
    user_uuid char(36) not null,
    server_uuid char(36) not null,
    role_uuid char(36) not null,
    foreign key (user_uuid) references user(uuid),
    foreign key (server_uuid) references server(uuid),
    foreign key (role_uuid) references role(uuid),
    primary key (user_uuid, server_uuid)
);
create table if not exists "session_token"(
    uuid char(36) primary key not null,
    name varchar(50) not null,
    hash char(64) not null,
    created_at DATETIME not null DEFAULT CURRENT_TIMESTAMP,
    last_used DATETIME not null,
    expires_at DATETIME,
    user_uuid char(36) not null,
    foreign key (user_uuid) references user(uuid)
);
create table if not exists "server_action"(
    uuid char(36) primary key not null,
    name varchar(255) not null,
    action_time DATETIME not null DEFAULT CURRENT_TIMESTAMP,
    user_uuid char(36) not null,
    server_uuid char(36) not null,
    action_type varchar(50) not null,
    foreign key (user_uuid) references user(uuid),
    foreign key (server_uuid) references server(uuid)
);
insert into "server"(uuid, name, type) values ("00000000-0000-0000-0000-000000000000", "global", "vanilla") ON CONFLICT(uuid) DO NOTHING;
insert into "role"(uuid, name, system_role,is_administrator) values ("00000000-0000-0000-0000-000000000000", "admin", 1,1) ON CONFLICT(uuid) DO NOTHING;
insert into "role"(uuid, name, system_role,can_see_console,can_use_console, can_see_history) values ("00000000-0000-0000-0000-000000000001", "moderator", 1,1,1,1) ON CONFLICT(uuid) DO NOTHING;
insert into "role"(uuid, name, system_role, can_control_state,can_modify,can_access_files,can_see_console,can_use_console) values ("00000000-0000-0000-0000-000000000002", "co-admin", 1,1,1,1,1,1) ON CONFLICT(uuid) DO NOTHING;
insert into "role"(uuid, name, system_role,can_start) values ("00000000-0000-0000-0000-000000000003", "user", 1,1) ON CONFLICT(uuid) DO NOTHING;
insert into "role"(uuid, name, system_role) values ("00000000-0000-0000-0000-000000000004", "viewer", 1) ON CONFLICT(uuid) DO NOTHING;