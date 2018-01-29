EUI.MainHomeView = EUI.extend(EUI.CustomUI, {
    renderTo: null,
    menudata: null,
    data: null,
    tabListener: {},
    initComponent: function () {
        EUI.Container({
            renderTo: this.renderTo,
            layout: "border",
            padding: 0,
            isOverFlow: false,
            itemspace: 0,
            defaultConfig: {
                border: false
            },
            items: [this.getCenter(), {
                region: "west",
                padding: 0,
                collapsible: true,
                initCollapse: function () {

                },
                addCollapseEvent: function () {
                    var g = this;
                    $(".hide-icon").live("click", function () {
                        g._doCollapseAndExpand();
                    });
                },
                afterCollapse: function (div) {
                    if (!div.content) {
                        div.content = $("<div class='ecmp-common-right-expand menu-collapse-title'></div>");
                        div.append(div.content);
                    }
                },
                id: "westTree",
                width: 240,
                isOverFlow: false,
                html: "<div class='user-manage'>" +
                "<div class='user-manage-icon'></div>" +
                "<div class='title'>" + __SessionUser.userInfo + "</div></div>" +
                "<div id='main_menu'>" +
                "<div class='menu-item-box'></div>" +
                "<div class='menu-scrollbar'><div class='menu-scrollbar-block'></div></div></div>"
                // + "<div class='menu-hide'><div class='tree-page'>&lt;&nbsp;&nbsp;&nbsp;1/3页&nbsp;&nbsp;&nbsp;&gt;</div></div>"
                // + "<div class='menu-hide'><div class='hide-icon'></div></div>"
            }]
        });
        this.addEvent();
        this.getMenuData();
        var height = $("#westTree").height() - 60;
        $("#main_menu").height(height);
        $(".menu-scrollbar").height(height);
    },
    getTopHtml: function () {
        return '<div class="home-top-left">' +
            '        <div class="home-logo"></div>' +
            '        <div class="home-logo-text">快益点渠道智能管理系统</div>' +
            '    </div>' +
            '<div class="home-more">' +
            '</div>' +
            '<div class="home-top-menu"></div>' +
            '    <div class="home-top-right">' +
            '        <div class="home-setting user">' +
            '            <div class="icon icon-user"></div>' +
            // '            <div class="title">' + __SessionUser.userInfo + '</div>' +
            '        </div>' +
            '        <div class="home-setting setting">' +
            '            <div class="icon icon-setting"></div>' +
            '            <div class="title">个人设置</div>' +
            '        </div>' +
            '        <div class="home-setting logout">' +
            '            <div class="icon icon-logout"></div>' +
            '            <div class="title">退出</div>' +
            '        </div>' +
            '    </div>';
    },
    getCenter: function () {
        var g = this;
        return {
            region: "center",
            xtype: "Container",
            layout: "border",
            padding: 0,
            itemspace: 0,
            items: [{
                region: "north",
                height: 40,
                border: false,
                padding: 0,
                style: {
                    background: "#fff",
                    color: "#000"
                },
                isOverFlow: false,
                html: this.getTopHtml()
            }, {
                region: "center",
                xtype: "TabPanel",
                id: "tabPanel",
                maxTabs: 15,//最多可以开15个页签
                isOverFlow: false,
                style: {
                    "background": "#fff"
                },
                onActive: function (id, win) {
                    if (!g.tabListener[id]) {
                        return;
                    }
                    g.tabListener[id].call(g, id, win);
                },
                items: [{
                    title: "首页",
                    closable: false,
                    // iframe:false,
                    url: _ctxPath + "/server/showServer"
                }]
            }]
        };
    },
    getMenuData: function () {
        var g = this;
        var mask = EUI.LoadMask();
        EUI.Store({
            url: _ctxPath + "/server/list",
            success: function (status) {
                status = {
                    "success": true,
                    "msg": "操作成功",
                    "code": null,
                    "data": [{
                        "id": "464b82088e7947358507b9fe9e25b85f",
                        "code": "10032",
                        "name": "工单管理",
                        "rank": 1,
                        "nodeLevel": 0,
                        "parentId": null,
                        "codePath": "|10032",
                        "namePath": "/工单管理",
                        "children": [{
                            "id": "120493f6db664f2a98058a595cbe878a",
                            "code": "10033",
                            "name": "工单管理",
                            "rank": 1,
                            "nodeLevel": 1,
                            "parentId": "464b82088e7947358507b9fe9e25b85f",
                            "codePath": "|10032|10033",
                            "namePath": "/工单管理/工单管理",
                            "children": null,
                            "featureUrl": "/workorder/showEtHeader"
                        }],
                        "featureUrl": null
                    }, {
                        "id": "50e9e0663c42400fbc6edbc77806a253",
                        "code": "10027",
                        "name": "业务管理",
                        "rank": 1,
                        "nodeLevel": 0,
                        "parentId": null,
                        "codePath": "|10027",
                        "namePath": "/业务管理",
                        "children": [{
                            "id": "bc5461dd81b54ef9aa57c7756c9caa5f",
                            "code": "10028",
                            "name": "订单管理",
                            "rank": 1,
                            "nodeLevel": 1,
                            "parentId": "50e9e0663c42400fbc6edbc77806a253",
                            "codePath": "|10027|10028",
                            "namePath": "/业务管理/订单管理",
                            "children": null,
                            "featureUrl": "user/MiniprogOrderManage"
                        }, {
                            "id": "faa55cfd9d9148f1ad4f44807622c858",
                            "code": "10029",
                            "name": "生成分公司二维码",
                            "rank": 1,
                            "nodeLevel": 1,
                            "parentId": "50e9e0663c42400fbc6edbc77806a253",
                            "codePath": "|10027|10029",
                            "namePath": "/业务管理/生成分公司二维码",
                            "children": null,
                            "featureUrl": "/mobile/qrCode/index"
                        }],
                        "featureUrl": null
                    }, {
                        "id": "e29465bf368141aab166e5dfa23bd1e0",
                        "code": "10004",
                        "name": "基础数据",
                        "rank": 1,
                        "nodeLevel": 0,
                        "parentId": null,
                        "codePath": "|10004",
                        "namePath": "/基础数据",
                        "children": [{
                            "id": "51e50ac0265840fda5e20ae3be671057",
                            "code": "10005",
                            "name": "基础数据",
                            "rank": 1,
                            "nodeLevel": 1,
                            "parentId": "e29465bf368141aab166e5dfa23bd1e0",
                            "codePath": "|10004|10005",
                            "namePath": "/基础数据/基础数据",
                            "children": null,
                            "featureUrl": "user/basicdata"
                        }, {
                            "id": "60a0bef370224f2db86fdb82f4777ce3",
                            "code": "10016",
                            "name": "产品品牌",
                            "rank": 1,
                            "nodeLevel": 1,
                            "parentId": "e29465bf368141aab166e5dfa23bd1e0",
                            "codePath": "|10004|10016",
                            "namePath": "/基础数据/产品品牌",
                            "children": null,
                            "featureUrl": "basic/brand/index"
                        }, {
                            "id": "a240af2394bf4938ae579545101f2384",
                            "code": "10006",
                            "name": "产品大类",
                            "rank": 1,
                            "nodeLevel": 1,
                            "parentId": "e29465bf368141aab166e5dfa23bd1e0",
                            "codePath": "|10004|10006",
                            "namePath": "/基础数据/产品大类",
                            "children": null,
                            "featureUrl": "basic/classify/index"
                        }, {
                            "id": "16e92c6c135b4190911ee6d7d0bbd010",
                            "code": "10016",
                            "name": "区域管理",
                            "rank": 3,
                            "nodeLevel": 1,
                            "parentId": "e29465bf368141aab166e5dfa23bd1e0",
                            "codePath": "|10004|10016",
                            "namePath": "/基础数据/区域管理",
                            "children": null,
                            "featureUrl": "basic/location/index"
                        }, {
                            "id": "584dd2c868974ce89a15513be9fcca38",
                            "code": "10008",
                            "name": "订单类型",
                            "rank": 4,
                            "nodeLevel": 1,
                            "parentId": "e29465bf368141aab166e5dfa23bd1e0",
                            "codePath": "|10004|10008",
                            "namePath": "/基础数据/订单类型",
                            "children": null,
                            "featureUrl": "basic/indenttype/index"
                        }, {
                            "id": "af265f59c7824b98a45be0c2b2c50f26",
                            "code": "10009",
                            "name": "订单价格",
                            "rank": 5,
                            "nodeLevel": 1,
                            "parentId": "e29465bf368141aab166e5dfa23bd1e0",
                            "codePath": "|10004|10009",
                            "namePath": "/基础数据/订单价格",
                            "children": null,
                            "featureUrl": "basic/delayprice/index"
                        }, {
                            "id": "d2c4c7fdb87a4691be5bd3206a928198",
                            "code": "10010",
                            "name": "订单状态",
                            "rank": 6,
                            "nodeLevel": 1,
                            "parentId": "e29465bf368141aab166e5dfa23bd1e0",
                            "codePath": "|10004|10010",
                            "namePath": "/基础数据/订单状态",
                            "children": null,
                            "featureUrl": "basic/orderstatus/index"
                        }, {
                            "id": "8983c6bbe9d84288a6a8a19a44691f79",
                            "code": "10011",
                            "name": "服务类型",
                            "rank": 7,
                            "nodeLevel": 1,
                            "parentId": "e29465bf368141aab166e5dfa23bd1e0",
                            "codePath": "|10004|10011",
                            "namePath": "/基础数据/服务类型",
                            "children": null,
                            "featureUrl": "basic/servetype/index"
                        }, {
                            "id": "2697ca49d0c24714a14ba82fecbeb816",
                            "code": "10012",
                            "name": "快益点片区",
                            "rank": 8,
                            "nodeLevel": 1,
                            "parentId": "e29465bf368141aab166e5dfa23bd1e0",
                            "codePath": "|10004|10012",
                            "namePath": "/基础数据/快益点片区",
                            "children": null,
                            "featureUrl": "basic/kydarea/index"
                        }, {
                            "id": "c63ced79c98f4e549cc5c92e372a1608",
                            "code": "10013",
                            "name": "快益点销售组织",
                            "rank": 9,
                            "nodeLevel": 1,
                            "parentId": "e29465bf368141aab166e5dfa23bd1e0",
                            "codePath": "|10004|10013",
                            "namePath": "/基础数据/快益点销售组织",
                            "children": null,
                            "featureUrl": "basic/kydorg/index"
                        }, {
                            "id": "868e46f11db641038e4136c42f335414",
                            "code": "10014",
                            "name": "多媒体组织",
                            "rank": 10,
                            "nodeLevel": 1,
                            "parentId": "e29465bf368141aab166e5dfa23bd1e0",
                            "codePath": "|10004|10014",
                            "namePath": "/基础数据/多媒体组织",
                            "children": null,
                            "featureUrl": "basic/dmtorg/index"
                        }],
                        "featureUrl": null
                    }, {
                        "id": "0",
                        "code": "HTPZ",
                        "name": "后台配置",
                        "rank": 1,
                        "nodeLevel": 0,
                        "parentId": null,
                        "codePath": "|HTPZ",
                        "namePath": "/后台配置",
                        "children": [{
                            "id": "1",
                            "code": "10145",
                            "name": "菜单管理",
                            "rank": 1,
                            "nodeLevel": 1,
                            "parentId": "0",
                            "codePath": "|HTPZ|10145",
                            "namePath": "/后台配置/菜单管理",
                            "children": null,
                            "featureUrl": "basic/menu/index"
                        }, {
                            "id": "f62a85be2084407fa8774c9785527239",
                            "code": "GHQPZ",
                            "name": "给号器配置",
                            "rank": 1,
                            "nodeLevel": 1,
                            "parentId": "0",
                            "codePath": "|HTPZ|GHQPZ",
                            "namePath": "/后台配置/给号器配置",
                            "children": null,
                            "featureUrl": "basic/serialNumberConfig/index"
                        }, {
                            "id": "0b7f497e832e4103a00fe8c11a2b3de4",
                            "code": "QXGL-QXDXLX",
                            "name": "权限对象类型",
                            "rank": 1,
                            "nodeLevel": 1,
                            "parentId": "0",
                            "codePath": "|HTPZ|QXGL-QXDXLX",
                            "namePath": "/后台配置/权限对象类型",
                            "children": null,
                            "featureUrl": "basic/authorizeEntityType/index"
                        }, {
                            "id": "5e0269eb34934681840feafc3a4a3821",
                            "code": "10003",
                            "name": "员工管理",
                            "rank": 1,
                            "nodeLevel": 1,
                            "parentId": "0",
                            "codePath": "|HTPZ|10003",
                            "namePath": "/后台配置/员工管理",
                            "children": null,
                            "featureUrl": "basic/userManage/index"
                        }, {
                            "id": "4028b8e55caa6d66015caa999b260008",
                            "code": "GNXGL",
                            "name": "功能项管理",
                            "rank": 2,
                            "nodeLevel": 1,
                            "parentId": "0",
                            "codePath": "|HTPZ|GNXGL",
                            "namePath": "/后台配置/功能项管理",
                            "children": null,
                            "featureUrl": "basic/featureGroup/index"
                        }, {
                            "id": "bf7cccaa52cc405c8d766b784ea12672",
                            "code": "10015",
                            "name": "应用模块",
                            "rank": 3,
                            "nodeLevel": 1,
                            "parentId": "0",
                            "codePath": "|HTPZ|10015",
                            "namePath": "/后台配置/应用模块",
                            "children": null,
                            "featureUrl": "basic/appmodule/index"
                        }, {
                            "id": "8fed7ae39a0840fa8957c47a98fd3150",
                            "code": "QXGL-SJQXLX",
                            "name": "数据权限类型",
                            "rank": 12,
                            "nodeLevel": 1,
                            "parentId": "0",
                            "codePath": "|HTPZ|QXGL-SJQXLX",
                            "namePath": "/后台配置/数据权限类型",
                            "children": null,
                            "featureUrl": "basic/dataAuthorizeType/index"
                        }],
                        "featureUrl": null
                    }, {
                        "id": "16bef9396dfd4b76a8c52ca897c071de",
                        "code": "KJGL",
                        "name": "会计管理",
                        "rank": 1,
                        "nodeLevel": 0,
                        "parentId": null,
                        "codePath": "|KJGL",
                        "namePath": "/会计管理",
                        "children": [{
                            "id": "5507f223f2014ba9b32f151278587262",
                            "code": "10026",
                            "name": "财务复核",
                            "rank": 1,
                            "nodeLevel": 1,
                            "parentId": "16bef9396dfd4b76a8c52ca897c071de",
                            "codePath": "|KJGL|10026",
                            "namePath": "/会计管理/财务复核",
                            "children": null,
                            "featureUrl": "core/pay/show/finPayBatch"
                        }, {
                            "id": "1020ffec9a9b4f83ab29cd5328f47f30",
                            "code": "FZCX",
                            "name": "分账查询",
                            "rank": 1,
                            "nodeLevel": 1,
                            "parentId": "16bef9396dfd4b76a8c52ca897c071de",
                            "codePath": "|KJGL|FZCX",
                            "namePath": "/会计管理/分账查询",
                            "children": null,
                            "featureUrl": "core/pay/show/paybatch"
                        }, {
                            "id": "157d782d82794a28b0b1ba2ae93cfdef",
                            "code": "TXQR",
                            "name": "提现确认",
                            "rank": 2,
                            "nodeLevel": 1,
                            "parentId": "16bef9396dfd4b76a8c52ca897c071de",
                            "codePath": "|KJGL|TXQR",
                            "namePath": "/会计管理/提现确认",
                            "children": null,
                            "featureUrl": "core/pay/show/txvoucher"
                        }, {
                            "id": "165fb83d90754ba3aada76ffb5bc3e86",
                            "code": "JDGL",
                            "name": "订单管理",
                            "rank": 3,
                            "nodeLevel": 1,
                            "parentId": "16bef9396dfd4b76a8c52ca897c071de",
                            "codePath": "|KJGL|JDGL",
                            "namePath": "/会计管理/订单管理",
                            "children": null,
                            "featureUrl": "core/payback"
                        }, {
                            "id": "14151a9952b849ec98163b896a4d482a",
                            "code": "10023",
                            "name": "分账财务复核",
                            "rank": 6,
                            "nodeLevel": 1,
                            "parentId": "16bef9396dfd4b76a8c52ca897c071de",
                            "codePath": "|KJGL|10023",
                            "namePath": "/会计管理/分账财务复核",
                            "children": null,
                            "featureUrl": "core/pay/show/paybatch"
                        }],
                        "featureUrl": null
                    }, {
                        "id": "690cf86345a64663bb838fe9d926b9e9",
                        "code": "ZSJGL",
                        "name": "主数据管理",
                        "rank": 1,
                        "nodeLevel": 0,
                        "parentId": null,
                        "codePath": "|ZSJGL",
                        "namePath": "/主数据管理",
                        "children": [{
                            "id": "db017f6aa92b4394819c397fd1e614c1",
                            "code": "FWSGL",
                            "name": "服务商管理",
                            "rank": 1,
                            "nodeLevel": 1,
                            "parentId": "690cf86345a64663bb838fe9d926b9e9",
                            "codePath": "|ZSJGL|FWSGL",
                            "namePath": "/主数据管理/服务商管理",
                            "children": null,
                            "featureUrl": "maindata/serviceProvider/index"
                        }, {
                            "id": "57fa0627be424a22a41179725bf22009",
                            "code": "FWSSH",
                            "name": "服务商审核",
                            "rank": 2,
                            "nodeLevel": 1,
                            "parentId": "690cf86345a64663bb838fe9d926b9e9",
                            "codePath": "|ZSJGL|FWSSH",
                            "namePath": "/主数据管理/服务商审核",
                            "children": null,
                            "featureUrl": "maindata/serviceProvider/audit/index"
                        }, {
                            "id": "5b3ce1fe73434ea4a3721add211f2c79",
                            "code": "10030",
                            "name": "区域展示",
                            "rank": 3,
                            "nodeLevel": 1,
                            "parentId": "690cf86345a64663bb838fe9d926b9e9",
                            "codePath": "|ZSJGL|10030",
                            "namePath": "/主数据管理/区域展示",
                            "children": null,
                            "featureUrl": "maindata/serviceProvider/overview"
                        }],
                        "featureUrl": null
                    }, {
                        "id": "70fad70ad6d94fcbb02a5a60c9b97de3",
                        "code": "XXGL",
                        "name": "消息管理",
                        "rank": 2,
                        "nodeLevel": 0,
                        "parentId": null,
                        "codePath": "|XXGL",
                        "namePath": "/消息管理",
                        "children": [{
                            "id": "7c3ebe6a0d044781bdc725c32037b1bf",
                            "code": "XXLB",
                            "name": "消息列表",
                            "rank": 1,
                            "nodeLevel": 1,
                            "parentId": "70fad70ad6d94fcbb02a5a60c9b97de3",
                            "codePath": "|XXGL|XXLB",
                            "namePath": "/消息管理/消息列表",
                            "children": null,
                            "featureUrl": "/core/msg/mqMsg"
                        }, {
                            "id": "73ee490b6a6d4f1fb0400d4c4785d534",
                            "code": "WFXX",
                            "name": "未发消息",
                            "rank": 2,
                            "nodeLevel": 1,
                            "parentId": "70fad70ad6d94fcbb02a5a60c9b97de3",
                            "codePath": "|XXGL|WFXX",
                            "namePath": "/消息管理/未发消息",
                            "children": null,
                            "featureUrl": "/core/msg/showSend"
                        }],
                        "featureUrl": null
                    }, {
                        "id": "9dda2e49a75d4295ad358228e0ed16d8",
                        "code": "10037",
                        "name": "服务质量监督",
                        "rank": 2,
                        "nodeLevel": 0,
                        "parentId": null,
                        "codePath": "|10037",
                        "namePath": "/服务质量监督",
                        "children": [{
                            "id": "23c43882ecef4af6ba78fed0e463e973",
                            "code": "10038",
                            "name": "聊天系统",
                            "rank": 1,
                            "nodeLevel": 1,
                            "parentId": "9dda2e49a75d4295ad358228e0ed16d8",
                            "codePath": "|10037|10038",
                            "namePath": "/服务质量监督/聊天系统",
                            "children": null,
                            "featureUrl": "wechat/userMsg/show"
                        }, {
                            "id": "ce39fe242cab46d79bb863b5cb509a8e",
                            "code": "10043",
                            "name": "订单管理",
                            "rank": 2,
                            "nodeLevel": 1,
                            "parentId": "9dda2e49a75d4295ad358228e0ed16d8",
                            "codePath": "|10037|10043",
                            "namePath": "/服务质量监督/订单管理",
                            "children": null,
                            "featureUrl": "core/serviceSupervision/index"
                        }],
                        "featureUrl": null
                    }, {
                        "id": "782ab399c7b54a46bf1fc64a359cced4",
                        "code": "DCWJ",
                        "name": "调查问卷",
                        "rank": 2,
                        "nodeLevel": 0,
                        "parentId": null,
                        "codePath": "|DCWJ",
                        "namePath": "/调查问卷",
                        "children": [{
                            "id": "07ae9a2d07d643f4adc22d5dddb844ab",
                            "code": "MBGL",
                            "name": "模版管理",
                            "rank": 1,
                            "nodeLevel": 1,
                            "parentId": "782ab399c7b54a46bf1fc64a359cced4",
                            "codePath": "|DCWJ|MBGL",
                            "namePath": "/调查问卷/模版管理",
                            "children": null,
                            "featureUrl": "questionnaire/template/index"
                        }, {
                            "id": "8a9ad4be2d5d43b6bb2a0600146e8ac3",
                            "code": "WJGL",
                            "name": "问卷管理",
                            "rank": 2,
                            "nodeLevel": 1,
                            "parentId": "782ab399c7b54a46bf1fc64a359cced4",
                            "codePath": "|DCWJ|WJGL",
                            "namePath": "/调查问卷/问卷管理",
                            "children": null,
                            "featureUrl": "questionnaire/user/index"
                        }, {
                            "id": "33bc6061aa144e15971e6c50d3cf5a2a",
                            "code": "WJJG",
                            "name": "问卷结果",
                            "rank": 3,
                            "nodeLevel": 1,
                            "parentId": "782ab399c7b54a46bf1fc64a359cced4",
                            "codePath": "|DCWJ|WJJG",
                            "namePath": "/调查问卷/问卷结果",
                            "children": null,
                            "featureUrl": "questionnaire/result/index"
                        }, {
                            "id": "20f73a955917412aa3b6e9ba5158d433",
                            "code": "10007",
                            "name": "查询",
                            "rank": 11,
                            "nodeLevel": 1,
                            "parentId": "782ab399c7b54a46bf1fc64a359cced4",
                            "codePath": "|DCWJ|10007",
                            "namePath": "/调查问卷/查询",
                            "children": null,
                            "featureUrl": "questionnaire/result/index"
                        }],
                        "featureUrl": null
                    }, {
                        "id": "4028b8e55caa6d66015caa96d3c80005",
                        "code": "ZZJG",
                        "name": "组织架构",
                        "rank": 3,
                        "nodeLevel": 0,
                        "parentId": null,
                        "codePath": "|ZZJG",
                        "namePath": "/组织架构",
                        "children": [{
                            "id": "4028b8e55cc92ef9015cc9337c550001",
                            "code": "ZZJG-GSGL",
                            "name": "公司管理",
                            "rank": 1,
                            "nodeLevel": 1,
                            "parentId": "4028b8e55caa6d66015caa96d3c80005",
                            "codePath": "|ZZJG|ZZJG-GSGL",
                            "namePath": "/组织架构/公司管理",
                            "children": null,
                            "featureUrl": "basic/corporation/index"
                        }, {
                            "id": "4028b8e55caa6d66015caa9b89050009",
                            "code": "ZZJGGL",
                            "name": "组织机构管理",
                            "rank": 3,
                            "nodeLevel": 1,
                            "parentId": "4028b8e55caa6d66015caa96d3c80005",
                            "codePath": "|ZZJG|ZZJGGL",
                            "namePath": "/组织架构/组织机构管理",
                            "children": null,
                            "featureUrl": "basic/organization/index"
                        }, {
                            "id": "4028b8e55caf4abf015caf4f42710001",
                            "code": "10052",
                            "name": "岗位类别管理",
                            "rank": 7,
                            "nodeLevel": 1,
                            "parentId": "4028b8e55caa6d66015caa96d3c80005",
                            "codePath": "|ZZJG|10052",
                            "namePath": "/组织架构/岗位类别管理",
                            "children": null,
                            "featureUrl": "basic/positionCategory/index"
                        }, {
                            "id": "4028b8e55caa6d66015caac31c830010",
                            "code": "GWGL",
                            "name": "岗位管理",
                            "rank": 10,
                            "nodeLevel": 1,
                            "parentId": "4028b8e55caa6d66015caa96d3c80005",
                            "codePath": "|ZZJG|GWGL",
                            "namePath": "/组织架构/岗位管理",
                            "children": null,
                            "featureUrl": "basic/position/index"
                        }],
                        "featureUrl": null
                    }, {
                        "id": "4028b8e55caa6d66015caac052b7000e",
                        "code": "QXGL",
                        "name": "权限管理",
                        "rank": 5,
                        "nodeLevel": 0,
                        "parentId": null,
                        "codePath": "|QXGL",
                        "namePath": "/权限管理",
                        "children": [{
                            "id": "4028b8e55caa6d66015caac2159e000f",
                            "code": "GNJSGL",
                            "name": "功能角色管理",
                            "rank": 7,
                            "nodeLevel": 1,
                            "parentId": "4028b8e55caa6d66015caac052b7000e",
                            "codePath": "|QXGL|GNJSGL",
                            "namePath": "/权限管理/功能角色管理",
                            "children": null,
                            "featureUrl": "basic/featureRole/index"
                        }, {
                            "id": "6f25ef8f7151481184dc56a485678120",
                            "code": "QXGL-SJJSGL",
                            "name": "数据角色管理",
                            "rank": 9,
                            "nodeLevel": 1,
                            "parentId": "4028b8e55caa6d66015caac052b7000e",
                            "codePath": "|QXGL|QXGL-SJJSGL",
                            "namePath": "/权限管理/数据角色管理",
                            "children": null,
                            "featureUrl": "basic/dataRole/index"
                        }],
                        "featureUrl": null
                    }, {
                        "id": "228f4ab4362f42d5b20060b328cde2a3",
                        "code": "10008",
                        "name": "根节点",
                        "rank": 11,
                        "nodeLevel": 0,
                        "parentId": null,
                        "codePath": "|10008",
                        "namePath": "/根节点",
                        "children": [{
                            "id": "b9d43e64694b463fb0e37ef3848b6b17",
                            "code": "10011",
                            "name": "工程师打款",
                            "rank": 1,
                            "nodeLevel": 1,
                            "parentId": "228f4ab4362f42d5b20060b328cde2a3",
                            "codePath": "|10008|10011",
                            "namePath": "/根节点/工程师打款",
                            "children": null,
                            "featureUrl": "core/engineerAward/index"
                        }, {
                            "id": "e617bc4d4683416c82cc6b274f13d5a3",
                            "code": "10014",
                            "name": "节点2",
                            "rank": 2,
                            "nodeLevel": 1,
                            "parentId": "228f4ab4362f42d5b20060b328cde2a3",
                            "codePath": "|10008|10014",
                            "namePath": "/根节点/节点2",
                            "children": null,
                            "featureUrl": null
                        }],
                        "featureUrl": null
                    }, {
                        "id": "1a02fafffa4f49bba8e97ac376cbec01",
                        "code": "10015",
                        "name": "根节点2",
                        "rank": 22,
                        "nodeLevel": 0,
                        "parentId": null,
                        "codePath": "|10015",
                        "namePath": "/根节点2",
                        "children": null,
                        "featureUrl": null
                    }],
                    "other": null
                };
                mask.hide();
                g.menudata = status.data;
                var menuData = [];
                for (var i = 0; i < status.data.length; i++) {
                    var css = "";
                    var item = status.data[i];
                    if (item.name == "基础数据") {
                        css = "base";
                    } else if (item.name == "后台配置") {
                        css = "config";
                    } else if (item.name == "会计管理") {
                        css = "account";
                    } else if (item.name == "主数据管理") {
                        css = "maindata";
                    } else if (item.name == "消息管理") {
                        css = "message";
                    } else if (item.name == "调查问卷") {
                        css = "research";
                    } else if (item.name == "组织架构") {
                        css = "organization";
                    } else if (item.name == "权限管理") {
                        css = "authority";
                    } else if (item.name == "业务管理") {
                        css = "business";
                    } else if (item.name == "根节点") {
                        css = "node";
                    }
                    menuData.push({
                        iconCss: css,
                        data: item
                    });
                }
                g.showMenu(menuData);
                $(".menu_item:first-child>.menu_parent", "#main_menu").trigger("click");
                g.showScrollBar();
            }, failure: function (status) {
                mask.hide();
                EUI.ProcessStatus(status);
            }
        });
        return [{}];
    },
    showMenu: function (data) {
        var g = this;
        var menudata = data;
        if (!menudata || menudata.length == 0) {
            return;
        }
        $(".menu_item").remove();
        var html = "";
        for (var i = 0; i < menudata.length; i++) {
            if (menudata[i].data.children) {
                html += "<div class='menu_item'><div class='menu_parent'>" +
                    "<div class='items_icon " + menudata[i].iconCss + "'></div>"
                    + menudata[i].data.name + "<div class='select_icon'></div></div><div class='menu_child'><div>"
                    + this.getChild(menudata[i].data.children, i == menudata.length - 1) + "</div></div></div>";
            }
        }
        $(".menu-item-box").append(html);
    },
    getChild: function (data, isLast) {
        var html = "";
        for (var i = 0; i < data.length; i++) {
            if (isLast && i == data.length - 1) {
                html += "<div><div class='menu-last'></div><div class='child_item last' data-more='" + data[i].canOpenMore + "' url='" + _ctxPath + '/' + data[i].featureUrl + "' id='" + data[i].id + "'>" + data[i].name + "</div></div>";
            } else {
                html += "<div class='child_item' data-more='" + data[i].canOpenMore + "' url='" + _ctxPath + '/' + data[i].featureUrl + "' id='" + data[i].id + "'>" + data[i].name + "</div>";
            }
        }
        return html;
    },
    showScrollBar: function () {
        var boxHeight = $("#main_menu").height();
        var itemHeight = $(".menu-item-box").height();
        if (boxHeight < itemHeight) {
            var bheight = 2 * boxHeight - itemHeight;
            bheight = bheight >= 0 ? bheight : 50;
            $(".menu-scrollbar").show().height(boxHeight);
            $(".menu-scrollbar-block").height(bheight);
            $(".menu-item-box").width(228);
        } else {
            $(".menu-scrollbar").hide();
            $(".menu-item-box").width("100%");
        }
    },
    addEvent: function () {
        var g = this;
        this.addTopEvent();
        this.addLeftEvent();
        $(window).bind("resize", function () {
            var height = $("#westTree").height() - 60;
            $("#main_menu").height(height);
            g.showScrollBar();
        });
    },
    addTopEvent: function () {
        var g = this;
        //退出
        $(".logout", ".home-top-right").bind("click", function () {
            window.location.href = _ctxPath + "/logout";
        });
        $(".app-item").live("click", function () {
            var index = $(this).attr("index");
            g.showMenu(g.menudata[index]);
            $(".home-app-dropdown").hide();
            $(".menu_parent").trigger("click");
        });
        $(".setting").bind("click", function () {
            g.addTab({
                title: "个人设置",
                id: "GRSZ",
                url: _ctxPath + "/basic/userProfile/index"
            })
        });
        $("body").bind("click", function () {
            $(".home-app-dropdown").hide();
        });
    },
    addLeftEvent: function () {
        var g = this;
        $(".menu_parent").live("click", function (e) {
            var item = $(this);
            var next = $(this).next();
            if (item.hasClass("menu_selected")) {
                item.removeClass("menu_selected");
                next.hide();
            } else {
                item.addClass("menu_selected");
                next.show();
            }
            g.showScrollBar();
            return false;
        });
        $(".menu-box").live("click", function () {
            $(this).addClass("select").siblings().removeClass("select");
        });
        $(".child_item").live("click", function () {
            var url = $(this).attr("url");
            var name = $(this).text();
            var moreOpen = $(this).attr("data-more");
            if (moreOpen == "true") {
                var tab = {
                    title: name,
                    url: url
                };
            } else {
                var tab = {
                    title: name,
                    url: url,
                    id: $(this).attr("id")
                };
            }
            g.addTab(tab);
        });
        // jquery 兼容的滚轮事件
        $("#main_menu").mousewheel(function (event, delta) {
            if ($(".menu-scrollbar").is(":hidden")) {
                return;
            }
            var boxDom = $(".menu-scrollbar-block");
            var offset = boxDom.position();
            var postion = $(".menu-item-box").position();
            var top, boxtop;
            //up
            if (delta > 0) {
                if (offset.top - 20 > 0) {
                    top = offset.top - 20;
                    boxtop = postion.top + 20;
                } else {
                    top = 0;
                    boxtop = 0;
                }
            } else {
                var bottom = boxDom.parent().height() - boxDom.height();
                if (offset.top + 20 >= bottom) {
                    top = bottom;
                    boxtop = -1 * bottom;
                } else {
                    top = offset.top + 20;
                    boxtop = postion.top - 20;
                }
            }
            boxDom.css({top: top, left: offset.left});
            $(".menu-item-box").css("top", boxtop);
        });
    },
    getTabPanel: function () {
        return EUI.getCmp("tabPanel");
    },
    addTab: function (tab, listenner) {
        var tabPanel = this.getTabPanel();
        tabPanel.addTab(tab);
        if (!tab.id || !listenner) {
            return;
        }
        this.tabListener[tab.id] = listenner;
    },
    closeNowTab: function () {
        var tabPanel = this.getTabPanel();
        tabPanel.close(tabPanel.activeId);
    },
    addTabListener: function (id, listenner) {
        if (!id || !listenner) {
            return;
        }
        this.tabListener[id] = listenner;
    }
});