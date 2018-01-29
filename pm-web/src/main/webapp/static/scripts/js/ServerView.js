
/*
 * 服务管理
 **/
EUI.ServerView = EUI.extend(EUI.CustomUI, {
    renderTo: "",
    initComponent: function () {
        EUI.Container({
            renderTo: this.renderTo,
            layout: "border",
            padding: 0,
            itemspace: 0,
            items: [this.initTop(), this.initCenter()]
        });
        this.addEvents();
    },
    initTop: function () {
        var g = this;
        return {
            xtype: "ToolBar",
            region: "north",
            height: 50,
            padding: 10,
            isOverFlow: false,
            border: false,
            items: [{
                xtype: "Button",
                title: "新增",
                selected: true,
                handler: function () {
                    g.showWindow();
                }
            }, {
                xtype: "Button",
                title: "编辑",
                selected: true,
                handler: function () {
                    var data = EUI.getCmp("gridPanel").getSelectRow();
                    if (data.length < 1) {
                        alert("请选择一条数据");
                    }
                    else if (data.length > 1) {
                        alert("只能选择一条数据");
                    } else {
                        g.editData();
                        //编辑弹窗默认数据
                        EUI.getCmp("updateServer").loadData(data[0]);
                    }
                }
            }, {
                xtype: "Button",
                style: {
                    "background": "red"
                },
                title: "删除",
                selected: true,
                handler: function () {
                    var data = EUI.getCmp("gridPanel").getSelectRow();
                    if (data.length < 1) {
                        alert("请至少选择一条")
                    }
                    else {
                        var ids = "";
                        for (var i = 0, len = data.length; i < len; i++) {
                            if (i == 0) ids += data[i].id;
                            else ids += "," + data[i].id;
                        }
                        console.log(ids);
                        g.deleteData(ids);
                    }
                }
            }, '->', {
                xtype: "SearchBox",
                displayText: "请输入...",
                onSearch: function (value) {
                    EUI.getCmp("gridPanel").setPostParams({
                        Quick_value: value,
                        Quick_property: "id,name,port,remark"
                    }, true);
                }
            }]
        }
    },
    initCenter: function () {
        var g = this;
        gridPanel = {
            xtype: "GridPanel",
            region: "center",
            id: "gridPanel",
            style: {
                "border-redius": "3px"
            },
            gridCfg: {
                url: _ctxPath + "/server/list",
                multiselect: true,
                shrinkToFit: false,
                colModel: [
                    {
                        name: "id",
                        hidden: true
                    }, {
                        name: "project.id",
                        hidden: true
                    }, {
                        label: "服务名",
                        name: "name",
                        align: "center",
                        width: 100,
                        sortable: true
                    }, {
                        label: "所属项目",
                        name: "project.name",
                        align: "center",
                        width: 100
                    }, {
                        label: "端口",
                        name: "port",
                        align: "center",
                        width: 100,
                        sortable: true
                    }, {
                        label: "创建时间",
                        name: "createTime",
                        align: "center",
                        width: 150,
                        sortable: true
                    }, {
                        label: "上次修改时间",
                        name: "lastUpdateTime",
                        align: "center",
                        width: 150
                    }, {
                        name: "status",
                        align: "center",
                        hidden: true,
                        width: 100
                    }, {
                        label: "状态",
                        align: "center",
                        width: 100,
                        formatter: function (cellvalue, options, rowData) {
                            if (rowData.status == "ENABLE")
                                return '<div style="color: green">启用</div>';
                            if (rowData.status == "DISABLE")
                                return '<div style="color: red">禁用</div>';
                        }
                    }, {
                        updateable: true,
                        label: "备注",
                        name: "remark",
                        align: "center",
                        width: 300
                    }],
                sortable: true,
                sortname: "createTime",
                sortorder: "desc"
            }
        };
        return gridPanel;
    },
    showWindow: function () {
        var g = this;
        var win = EUI.Window({
            title: "新增",
            height: 120,
            width: 300,
            padding: 10,
            items: [{
                xtype: "FormPanel",
                id: "saveServer",
                padding: 0,
                defaultConfig: {
                    labelWidth: 80,
                    width: 220
                },
                items: [{
                    xtype: "TextField",
                    title: "服务名",
                    name: "name",
                    allowBlank: false
                }, {
                    xtype: "NumberField",
                    title: "端口",
                    allowBlank: false,
                    name: "port"
                }, {
                    xtype: "TextField",
                    title: "备注",
                    name: "remark"
                }, {
                    xtype: "ComboBox",
                    title: "所属项目",
                    field: ["project.id"],
                    allowBlank: false,
                    name: "projectName",
                    store: {
                        url: _ctxPath + "/project/combo"
                    },
                    reader: {
                        field: ["id"],
                        name: "name"
                    },
                    displayText: "请选择分组"
                }]
            }],
            buttons: [{
                title: "保存",
                selected: true,
                handler: function () {
                    var form = EUI.getCmp("saveServer");
                    if (!form.isValid()) {
                        return;
                    }
                    var data = form.getFormValue();
                    g.saveData(data, function () {
                        win.close();
                    });
                }
            }, {
                title: "取消",
                handler: function () {
                    win.close();
                }
            }]
        });
    },
    saveData: function (data, callback) {
        var g = this;
        var myMask = EUI.LoadMask({
            msg: "正在保存，请稍候..."
        });
        EUI.Store({
            url: _ctxPath + "/server/save",
            params: data,
            success: function (result) {
                myMask.hide();
                EUI.ProcessStatus(result);
                callback && callback.call(this);
                EUI.getCmp("gridPanel").refreshGrid();
            },
            failure: function (result) {
                myMask.hide();
                EUI.ProcessStatus(result);
            }
        })
    },
    deleteData: function (ids) {
        var g = this;
        var infoBox = EUI.MessageBox({
            title: "提示消息",
            msg: "您确定要删除吗？",
            buttons: [{
                title: "确定",
                selected: true,
                handler: function () {
                    infoBox.remove();
                    var myMask = EUI.LoadMask({
                        msg: "正在删除，请稍后..."
                    });
                    EUI.Store({
                        url: _ctxPath + "/server/delete",
                        params: {
                            ids: ids
                        },
                        success: function (result) {
                            myMask.hide();
                            EUI.ProcessStatus(result);
                            EUI.getCmp("gridPanel").refreshGrid();
                        },
                        failure: function (result) {
                            EUI.ProcessStatus(result);
                            myMask.hide();
                        }
                    });
                }
            }, {
                title: "取消",
                handler: function () {
                    infoBox.remove();
                }
            }]
        });
    },
    addEvents: function () {
    },
    editData: function () {
        var g = this;
        var win = EUI.Window({
            title: "修改服务",
            height: 150,
            width: 300,
            padding: 15,
            items: [{
                xtype: "FormPanel",
                id: "updateServer",
                padding: 0,
                items: [{
                    xtype: "TextField",
                    name: "id",
                    hidden: true
                }, {
                    xtype: "TextField",
                    title: "服务名",
                    name: "name",
                    labelWidth: 56,
                    allowBlank: false,
                    width: 240
                }, {
                    xtype: "NumberField",
                    title: "端口",
                    name: "port",
                    labelWidth: 56,
                    allowBlank: false,
                    width: 240
                }, {
                    xtype: "ComboBox",
                    title: "所属项目",
                    field: ["project.id"],
                    name: "project.name",
                    store: {
                        url: _ctxPath + "/project/combo"
                    },
                    reader: {
                        field: ["id"],
                        name: "name"
                    },
                    displayText: "请选择分组"
                }, {
                    xtype: "RadioBoxGroup",
                    title: "状态",
                    labelWidth: 100,
                    itemspace: 10,
                    name: "status",
                    id: "status",
                    items: [
                        {
                            title: "启用",
                            name: "ENABLE",
                            onChecked: true
                        }, {
                            title: "禁用",
                            name: "DISABLE"
                        }]
                }, {
                    xtype: "TextField",
                    title: "备注",
                    name: "remark",
                    labelWidth: 56,
                    width: 240
                }
                ]
            }],
            buttons: [{
                title: "保存",
                selected: true,
                handler: function () {
                    var form = EUI.getCmp("updateServer");
                    if (!form.isValid()) {
                        return;
                    }
                    var data = form.getFormValue();
                    g.saveData(data, function () {
                        win.close();
                    });
                }
            }, {
                title: "取消",
                handler: function () {
                    win.remove();
                }
            }]
        });
    }
});