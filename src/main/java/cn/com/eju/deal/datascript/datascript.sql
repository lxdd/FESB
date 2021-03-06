USE [FESB]
GO
/****** Object:  Table [dbo].[BAS_DictionaryType]    Script Date: 2017/6/14 14:23:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[BAS_DictionaryType](
	[typeId] [int] NOT NULL,
	[typeName] [nvarchar](20) NOT NULL,
	[dateCreate] [datetime] NOT NULL,
	[delFlag] [char](1) NOT NULL,
 CONSTRAINT [PK_BAS_DictionaryType] PRIMARY KEY CLUSTERED 
(
	[typeId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[typeId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[BAS_DictionaryValue]    Script Date: 2017/6/14 14:23:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[BAS_DictionaryValue](
	[dicId] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[typeId] [int] NOT NULL,
	[dicCode] [int] NOT NULL,
	[dicValue] [nvarchar](30) NOT NULL,
	[dicSortNo] [int] NULL,
	[dateCreate] [datetime] NOT NULL,
	[delFlag] [char](1) NOT NULL,
	[dicGroup] [varchar](64) NULL,
 CONSTRAINT [PK_BAS_DictionaryValue] PRIMARY KEY CLUSTERED 
(
	[dicId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [UQ__BAS_Dict__91FDD46C2BC008F2] UNIQUE NONCLUSTERED 
(
	[dicCode] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[BAS_WebConfig]    Script Date: 2017/6/14 14:23:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[BAS_WebConfig](
	[webConfigId] [int] IDENTITY(1,1) NOT NULL,
	[webConfigName] [varchar](32) NULL,
	[webConfigValue] [nvarchar](256) NULL,
	[webConfigDesc] [nvarchar](64) NULL,
	[dateCreate] [datetime] NULL,
	[userIdCreate] [int] NULL,
	[delFlag] [char](1) NULL,
 CONSTRAINT [PK_BAS_WebConfig] PRIMARY KEY CLUSTERED 
(
	[webConfigId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[FESB_Apps_Client]    Script Date: 2017/6/14 14:23:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[FESB_Apps_Client](
	[appId] [int] NOT NULL,
	[appCode] [varchar](50) NULL,
	[appName] [nvarchar](50) NULL,
	[appKey] [nvarchar](50) NULL,
	[appSecret] [nvarchar](50) NULL,
	[authFlag] [bit] NULL CONSTRAINT [DF_FESB_Apps_Client_authFlag]  DEFAULT ((1)),
	[ipbwFlag] [bit] NULL CONSTRAINT [DF_FESB_Apps_Client_ipbwFlag]  DEFAULT ((0)),
	[dateCreate] [datetime] NULL CONSTRAINT [DF_FESB_Apps_Client_dateCreate]  DEFAULT (getdate()),
	[delFlag] [bit] NULL CONSTRAINT [DF_FESB_Apps_Client_delFlag]  DEFAULT ((0)),
 CONSTRAINT [PK_FESB_Apps_Client] PRIMARY KEY CLUSTERED 
(
	[appId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[FESB_AppsResourceMapping]    Script Date: 2017/6/14 14:23:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[FESB_AppsResourceMapping](
	[id] [int] NOT NULL,
	[appId] [int] NOT NULL,
	[resourceId] [int] NOT NULL,
	[dateCreate] [datetime] NULL CONSTRAINT [DF_FESB_AppsResourceMapping_dateCreate]  DEFAULT (getdate()),
	[delFlag] [bit] NULL CONSTRAINT [DF_FESB_AppsResourceMapping_delFlag]  DEFAULT ((0)),
 CONSTRAINT [PK_FESB_AppsResourceMapping] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[FESB_IP_BW]    Script Date: 2017/6/14 14:23:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[FESB_IP_BW](
	[id] [int] NOT NULL,
	[appId] [int] NOT NULL,
	[ipStartStr] [varchar](50) NULL,
	[ipStartInt] [int] NULL,
	[ipEndStr] [varchar](50) NULL,
	[ipEndInt] [int] NULL,
	[dateCreate] [datetime] NULL,
	[delFlag] [bit] NULL,
 CONSTRAINT [PK_FESB_IP_BW] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[FESB_Service_Resource]    Script Date: 2017/6/14 14:23:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[FESB_Service_Resource](
	[resourceId] [int] NOT NULL,
	[systemId] [int] NULL,
	[resourceCode] [varchar](50) NULL,
	[resourceName] [nvarchar](50) NULL,
	[resourceUrl] [varchar](50) NULL,
	[dateCreate] [datetime] NULL CONSTRAINT [DF_FESB_Service_Resource_dateCreate]  DEFAULT (getdate()),
	[delFlag] [bit] NULL CONSTRAINT [DF_FESB_Service_Resource_delFlag]  DEFAULT ((0)),
 CONSTRAINT [PK_FESB_Service_Resource] PRIMARY KEY CLUSTERED 
(
	[resourceId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[FESB_Service_System]    Script Date: 2017/6/14 14:23:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[FESB_Service_System](
	[systemId] [int] NOT NULL,
	[systemCode] [varchar](50) NULL,
	[systemName] [nvarchar](50) NULL,
	[systemAddr] [varchar](50) NULL,
	[dateCreate] [datetime] NULL CONSTRAINT [DF_FESB_Service_System_dateCreate]  DEFAULT (getdate()),
	[delFlag] [bit] NULL CONSTRAINT [DF_FESB_Service_System_delFlag]  DEFAULT ((0)),
 CONSTRAINT [PK_FESB_Service_System] PRIMARY KEY CLUSTERED 
(
	[systemId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[LOG_Error]    Script Date: 2017/6/14 14:23:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[LOG_Error](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[moduleName] [varchar](64) NULL,
	[className] [varchar](64) NULL,
	[methodName] [varchar](64) NULL,
	[parameter] [nvarchar](max) NULL,
	[ipAddress] [varchar](128) NULL,
	[exceptionInfo] [nvarchar](max) NULL,
	[description] [nvarchar](max) NULL,
	[userIdCreate] [int] NULL,
	[dateCreate] [datetime] NULL CONSTRAINT [DF_LOG_Error_dateCreate]  DEFAULT ('getdate()'),
	[delFlag] [char](1) NULL CONSTRAINT [DF_LOG_Error_delFlag]  DEFAULT ('N'),
	[systemName] [nvarchar](20) NULL,
 CONSTRAINT [PK_LOG_Error] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
ALTER TABLE [dbo].[BAS_DictionaryType] ADD  CONSTRAINT [DF_BAS_DictionaryType_dateCreate]  DEFAULT (getdate()) FOR [dateCreate]
GO
ALTER TABLE [dbo].[BAS_DictionaryType] ADD  CONSTRAINT [DF_BAS_DictionaryType_delFlag]  DEFAULT ('N') FOR [delFlag]
GO
ALTER TABLE [dbo].[BAS_DictionaryValue] ADD  CONSTRAINT [DF_BAS_DictionaryValue_dicSortNo]  DEFAULT ((0)) FOR [dicSortNo]
GO
ALTER TABLE [dbo].[BAS_DictionaryValue] ADD  CONSTRAINT [DF_BAS_DictionaryValue_dateCreate]  DEFAULT (getdate()) FOR [dateCreate]
GO
ALTER TABLE [dbo].[BAS_DictionaryValue] ADD  CONSTRAINT [DF_BAS_DictionaryValue_delFlag]  DEFAULT ((0)) FOR [delFlag]
GO
ALTER TABLE [dbo].[BAS_WebConfig] ADD  CONSTRAINT [DF_BAS_WebConfig_dateCreate]  DEFAULT (getdate()) FOR [dateCreate]
GO
ALTER TABLE [dbo].[BAS_WebConfig] ADD  CONSTRAINT [DF_BAS_WebConfig_delFlag]  DEFAULT ('N') FOR [delFlag]
GO
ALTER TABLE [dbo].[FESB_IP_BW] ADD  CONSTRAINT [DF_FESB_IP_BW_dateCreate]  DEFAULT (getdate()) FOR [dateCreate]
GO
ALTER TABLE [dbo].[FESB_IP_BW] ADD  CONSTRAINT [DF_FESB_IP_BW_delFlag]  DEFAULT ((0)) FOR [delFlag]
GO
ALTER TABLE [dbo].[BAS_DictionaryValue]  WITH CHECK ADD  CONSTRAINT [FK_BAS_DictionaryValue_BAS_DictionaryType] FOREIGN KEY([typeId])
REFERENCES [dbo].[BAS_DictionaryType] ([typeId])
GO
ALTER TABLE [dbo].[BAS_DictionaryValue] CHECK CONSTRAINT [FK_BAS_DictionaryValue_BAS_DictionaryType]
GO
ALTER TABLE [dbo].[FESB_AppsResourceMapping]  WITH CHECK ADD  CONSTRAINT [FK_FESB_AppsResourceMapping_FESB_Service_Resource] FOREIGN KEY([appId])
REFERENCES [dbo].[FESB_Apps_Client] ([appId])
GO
ALTER TABLE [dbo].[FESB_AppsResourceMapping] CHECK CONSTRAINT [FK_FESB_AppsResourceMapping_FESB_Service_Resource]
GO
ALTER TABLE [dbo].[FESB_AppsResourceMapping]  WITH CHECK ADD  CONSTRAINT [FK_FESB_AppsResourceMapping_FESB_Service_Resource1] FOREIGN KEY([resourceId])
REFERENCES [dbo].[FESB_Service_Resource] ([resourceId])
GO
ALTER TABLE [dbo].[FESB_AppsResourceMapping] CHECK CONSTRAINT [FK_FESB_AppsResourceMapping_FESB_Service_Resource1]
GO
ALTER TABLE [dbo].[FESB_IP_BW]  WITH CHECK ADD  CONSTRAINT [FK_FESB_IP_BW_FESB_Apps_Client] FOREIGN KEY([appId])
REFERENCES [dbo].[FESB_Apps_Client] ([appId])
GO
ALTER TABLE [dbo].[FESB_IP_BW] CHECK CONSTRAINT [FK_FESB_IP_BW_FESB_Apps_Client]
GO
ALTER TABLE [dbo].[FESB_Service_Resource]  WITH CHECK ADD  CONSTRAINT [FK_FESB_Service_Resource_FESB_Service_System] FOREIGN KEY([systemId])
REFERENCES [dbo].[FESB_Service_System] ([systemId])
GO
ALTER TABLE [dbo].[FESB_Service_Resource] CHECK CONSTRAINT [FK_FESB_Service_Resource_FESB_Service_System]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'类型Id，不自增，唯一' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'BAS_DictionaryType', @level2type=N'COLUMN',@level2name=N'typeId'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'类型名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'BAS_DictionaryType', @level2type=N'COLUMN',@level2name=N'typeName'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'字典Id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'BAS_DictionaryValue', @level2type=N'COLUMN',@level2name=N'dicId'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'类型Id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'BAS_DictionaryValue', @level2type=N'COLUMN',@level2name=N'typeId'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'字典编码' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'BAS_DictionaryValue', @level2type=N'COLUMN',@level2name=N'dicCode'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'字典值' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'BAS_DictionaryValue', @level2type=N'COLUMN',@level2name=N'dicValue'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'字典分组' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'BAS_DictionaryValue', @level2type=N'COLUMN',@level2name=N'dicGroup'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'配置Id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'BAS_WebConfig', @level2type=N'COLUMN',@level2name=N'webConfigId'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'配置名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'BAS_WebConfig', @level2type=N'COLUMN',@level2name=N'webConfigName'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'配置值' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'BAS_WebConfig', @level2type=N'COLUMN',@level2name=N'webConfigValue'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'配置描述' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'BAS_WebConfig', @level2type=N'COLUMN',@level2name=N'webConfigDesc'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'应用端Code' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'FESB_Apps_Client', @level2type=N'COLUMN',@level2name=N'appCode'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'应用端name' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'FESB_Apps_Client', @level2type=N'COLUMN',@level2name=N'appName'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'应用端key' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'FESB_Apps_Client', @level2type=N'COLUMN',@level2name=N'appKey'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'应用端secret' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'FESB_Apps_Client', @level2type=N'COLUMN',@level2name=N'appSecret'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'应用端认证标示' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'FESB_Apps_Client', @level2type=N'COLUMN',@level2name=N'authFlag'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'应用端IP黑白名单认证标示' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'FESB_Apps_Client', @level2type=N'COLUMN',@level2name=N'ipbwFlag'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'应用端主键Id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'FESB_AppsResourceMapping', @level2type=N'COLUMN',@level2name=N'appId'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'服务资源主键Id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'FESB_AppsResourceMapping', @level2type=N'COLUMN',@level2name=N'resourceId'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'应用端主键Id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'FESB_IP_BW', @level2type=N'COLUMN',@level2name=N'appId'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'服务系统主键Id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'FESB_Service_Resource', @level2type=N'COLUMN',@level2name=N'systemId'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'服务资源code' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'FESB_Service_Resource', @level2type=N'COLUMN',@level2name=N'resourceCode'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'服务资源name' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'FESB_Service_Resource', @level2type=N'COLUMN',@level2name=N'resourceName'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'服务资源Url' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'FESB_Service_Resource', @level2type=N'COLUMN',@level2name=N'resourceUrl'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'服务系统Code' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'FESB_Service_System', @level2type=N'COLUMN',@level2name=N'systemCode'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'服务系统名' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'FESB_Service_System', @level2type=N'COLUMN',@level2name=N'systemName'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'服务系统地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'FESB_Service_System', @level2type=N'COLUMN',@level2name=N'systemAddr'
GO
