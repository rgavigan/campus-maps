import{j as e,c as y,o as P,s as U,u as A,r as w,P as ce,N as de,O as $,Q as pe,E as M,y as X,z as Y,D as Z,X as Q,Y as J,F as O,i as he,q as ue}from"./vendor-95b30d29.js";import{T as xe,u as I,C as ae,a as le,F as m,H as q,D as F,c as b,I as k,b as _,d as E,A as V,e as G,S as je,f as T,B as ee,g as re,h as se,i as z,j as ge,k as me,l as ve,m as te,G as we,N as ne,n as oe}from"./index-61e14e01.js";import{P as ie}from"./PageHeader-c1f22813.js";import{c as d,G as W}from"./Overview.module-c4ab1fc0.js";const be="_root_1whq5_1",Se="_darkerBlue_1whq5_12",fe="_darkBlue_1whq5_16",Ce="_blue_1whq5_20",ye="_lightBlue_1whq5_24",_e="_success_1whq5_28",ke="_warning_1whq5_32",Ne="_danger_1whq5_36",Ae="_neutral_1whq5_40",Oe="_cursor_1whq5_59",R={root:be,darkerBlue:Se,darkBlue:fe,blue:Ce,lightBlue:ye,success:_e,warning:ke,danger:Ne,neutral:Ae,cursor:Oe},N=({count:s,title:x,tooltip:o,color:j="neutral",className:t,onClick:r,enabled:h})=>{const c=()=>e.jsxs("div",{onClick:r,className:y(R.root,t,{[R.cursor]:r},{[R[j]]:h||typeof s=="number"&&s}),children:[e.jsx("h3",{children:s}),e.jsx("small",{children:x})]});return o?e.jsx(xe,{content:o,children:e.jsx("span",{children:e.jsx(c,{})})}):e.jsx(c,{})},Te=P(({server:s})=>{var L;const x=()=>{let p=0;if(s.apps)for(const K of s.apps)for(const D of K.status)D.status==="Running"&&p++;return p},o=()=>{var K,D;const p=x();return p===((K=s.apps)==null?void 0:K.length)?"success":p===0&&p!==((D=s.apps)==null?void 0:D.length)?"danger":"warning"},j=()=>{var p;return x()+" / "+((p=s.apps)==null?void 0:p.length)},t=()=>s.instancesConnected+" / "+s.idle+" / "+s.instances,r=()=>s.instancesConnected+" / "+s.instances,{logsStore:h,overviewStore:c}=I(),v=U(),{t:l,i18n:a}=A(),[n,i]=w.useState(!1),[u,f]=w.useState(!1),g=w.useCallback(()=>{i(!n)},[n]),C=w.useCallback(()=>{f(!u)},[u]),B=w.useCallback(p=>{h.setServerLogId(p),v.push("logs/server")},[v]);return e.jsx(ae,{className:d.serverCard,children:e.jsxs(le,{children:[e.jsxs(m,{justifyContent:"space-between",children:[e.jsx(q,{variant:"h2",classes:{heading:d.cardHeading},title:s.cluster?s.id:"Webswing Server"}),e.jsx(F,{position:"right",buttons:[{label:l("overview.verboseMode"),icon:"info",onClick:()=>c.toggleVerbose(s,"server")},{label:l("routes.logs"),icon:"list_alt",onClick:()=>B(s.id)}],children:e.jsx("span",{className:y(b.wButtonDefault,b.medium,b.neutral,b.icon),children:e.jsx(k,{name:"more_vert"})})})]}),e.jsx("span",{className:d.neuralSub,children:s.websocketUrl}),e.jsx(m,{spacing:2,className:y(d.header,{[d.chipsLine]:c.isThereAnyServerVerbose}),children:s.verboseLogging&&e.jsxs(_,{size:"small",children:[e.jsx(k,{name:"check_circle_outline",size:"mini"})," ",l("overview.verboseMode")]})}),e.jsxs(m,{spacing:3,flexWrap:"wrap",children:[e.jsx(N,{title:l("sessions.sp_plural"),count:(L=s.sessionPools)==null?void 0:L.length,color:"darkerBlue"}),e.jsx(N,{title:l("overview.users"),count:s.users,color:"darkBlue",tooltip:u?"":l("overview.users.tooltip"),onClick:C}),s.cluster&&e.jsx(N,{title:l("overview.sessions"),count:r(),color:"blue",enabled:s.instances>0,tooltip:l("overview.sessionsCluster.tooltip")}),!s.cluster&&e.jsx(N,{title:l("overview.sessions"),count:t(),color:"blue",enabled:s.instances>0||s.idle>0,tooltip:l("overview.sessions.tooltip")}),e.jsx(N,{title:l("overview.connections"),count:s.connections,color:"lightBlue",tooltip:l("overview.connections.tooltip")}),e.jsx(N,{title:l("overview.appsRunning"),count:j(),color:o(),enabled:!0,onClick:g}),e.jsx(E,{bOpen:n,title:l("overview.availableApps"),onToggle:g,footer:!1,children:e.jsx("div",{className:d.spTable,children:e.jsxs("table",{children:[e.jsx("thead",{children:e.jsxs("tr",{children:[e.jsx("td",{children:l("routes.apps")}),e.jsx("td",{children:l("general.status")})]})}),e.jsx("tbody",{children:s.apps.map(p=>e.jsxs("tr",{children:[e.jsx("td",{children:e.jsxs(m,{spacing:2,children:[e.jsx(V,{shape:"square",src:G+p.path+"/rest/appicon"}),e.jsxs("div",{children:[p.name,e.jsx("small",{children:p.path})]})]})}),e.jsx("td",{children:e.jsx(je,{label:p.status[0].status})})]},p.name))})]})})}),e.jsx(E,{bOpen:u,title:l("overview.users"),onToggle:C,footer:!1,children:e.jsx("div",{className:d.userListTable,children:e.jsx("table",{children:e.jsxs("tbody",{children:[s.userList&&s.userList.map(p=>e.jsx("tr",{children:e.jsx("td",{children:p})},p)),!s.userList&&e.jsx("tr",{children:e.jsx("td",{children:l("overview.users.empty")})})]})})})})]})]})})}),Fe=({className:s,width:x,height:o,onClick:j,apps:t})=>{const[r,h]=w.useState(!0),{t:c}=A(),v=()=>Object.entries(t).map(n=>({name:n[0],value:n[1]})),l=()=>{let n=0;return Object.entries(t).forEach(i=>{n+=i[1]}),n};w.useEffect(()=>{h(!l())},[t]);const a=["#0088FE","#00C49F","#FFBB28","#FF8042"];return e.jsx(e.Fragment,{children:e.jsxs(ce,{onClick:j,className:s,width:x,height:o,children:[e.jsxs(de,{data:r?[{name:"no",value:1}]:v(),innerRadius:46,outerRadius:58,fill:"#8884d8",paddingAngle:0,dataKey:"value",children:[e.jsx($,{value:l(),position:"centerBottom",className:d.cakeLabelTop,fontSize:"27px"}),e.jsx($,{value:c("overview.running"),position:"centerTop",className:d.cakeLabelBottom}),v().map((n,i)=>e.jsx(pe,{fill:r?"#E5E5E6":a[i%a.length]},`cell-${i}`))]}),!r&&e.jsx(M,{})]})})},H=P(({className:s,onClick:x,label:o,dialogHeader:j,children:t})=>{const[r,h]=w.useState(!1),c=w.useCallback(v=>{r!==v&&h(v||!r)},[r]);return e.jsxs(e.Fragment,{children:[e.jsx("span",{onClick:()=>h(!r),className:y(T.wCell,d.link),style:{justifyContent:"start"},children:o}),e.jsx(E,{bOpen:r,title:j,onToggle:c,footer:!1,children:t})]})}),Pe=({className:s,onClick:x,sp:o,clusterservers:j=[]})=>{const[t,r]=w.useState(!1),{logsStore:h,overviewStore:c,globalStore:v}=I(),l=U(),{t:a}=A(),n=()=>Object.entries(o.apps).map(g=>{var B;const C=(B=v.paths)==null?void 0:B.find(L=>L.path===g[0]);return{name:C==null?void 0:C.name,path:g[0],value:g[1]}}),i=()=>{var C;return Object.entries(o.apps).length+" / "+((C=j[0].apps)==null?void 0:C.length)},u=w.useCallback(g=>{h.setSpLogId(g),l.push("logs/sessionpool")},[l]),f=()=>o.drainMode?a("overview.resume"):a("overview.drainMode");return e.jsx(ae,{onClick:x,className:y(d.spCard,s),children:e.jsxs(le,{children:[e.jsxs(m,{justifyContent:"space-between",children:[e.jsx(q,{variant:"h2",classes:{heading:d.cardHeading},title:o.id}),!o.stopped&&e.jsx(F,{position:"right",buttons:[{label:a("overview.verboseMode"),icon:"info",onClick:()=>c.toggleVerbose(o,"sp")},{label:a("routes.logs"),icon:"list_alt",onClick:()=>u(o.id)},{label:f(),icon:"opacity",onClick:()=>c.toggleDrain(o)},{label:a("overview.stop"),icon:"power_settings_new",onClick:()=>c.stop(o.id),shouldShow:!c.hasRunningInstances(o.apps)},{label:a("overview.shutdownSessions"),icon:"power_settings_new",onClick:()=>c.shutdown(o),shouldShow:c.hasRunningInstances(o.apps)}],children:e.jsx("span",{className:y(b.wButtonDefault,b.medium,b.neutral,b.icon),children:e.jsx(k,{name:"more_vert"})})})]}),e.jsxs(m,{spacing:2,className:y(d.header,{[d.chipsLine]:c.isThereAnySPVerbose}),children:[o.stopped?e.jsx(_,{size:"small",color:"danger",children:a("overview.stopped")}):e.jsx(_,{size:"small",color:"success",children:a("overview.running")}),o.staticSP&&e.jsx(_,{size:"small",tooltip:a("overview.staticSP.tooltip"),children:a("overview.staticSP")}),!o.stopped&&o.drainMode&&e.jsxs(_,{size:"small",color:"warning",children:[e.jsx(k,{name:"opacity",size:"mini"})," ",o.drainAutoMode?a("overview.drainAutoMode"):a("overview.drainMode")]}),o.verboseLogging&&e.jsxs(_,{size:"small",children:[e.jsx(k,{name:"check_circle_outline",size:"mini"})," ",a("overview.verboseMode")]})]}),e.jsxs(m,{spacing:3,flexWrap:"wrap",children:[e.jsx(m,{flex:1,children:e.jsx(Fe,{apps:o.apps,width:130,height:130})}),e.jsx(m,{flex:2,children:e.jsx("table",{className:d.spTable,children:e.jsxs("tbody",{children:[e.jsxs("tr",{children:[e.jsx("td",{children:e.jsxs("strong",{children:[a("overview.maxSessions"),":"]})}),e.jsx("td",{children:o.maxInstances==-1?a("overview.unlimited"):o.maxInstances})]}),e.jsxs("tr",{children:[e.jsx("td",{children:e.jsxs("strong",{children:[a("overview.maxIdleSessions"),":"]})}),e.jsx("td",{children:o.maxIdleInstances==-1?a("overview.unlimited"):o.maxIdleInstances})]}),e.jsxs("tr",{children:[e.jsx("td",{children:e.jsxs("strong",{children:[a("overview.idleInstances"),":"]})}),e.jsx("td",{children:o.idle})]}),e.jsxs("tr",{children:[e.jsx("td",{children:e.jsxs("strong",{children:[a("general.priority"),":"]})}),e.jsx("td",{children:o.priority})]}),e.jsxs("tr",{onClick:()=>r(!t),children:[e.jsx("td",{children:e.jsxs("strong",{children:[a("routes.apps"),":"]})}),e.jsx("td",{children:e.jsx(H,{dialogHeader:a("overview.availableAppsforSP"),label:i(),children:e.jsxs("table",{className:d.spTable,children:[e.jsx("thead",{children:e.jsxs("tr",{children:[e.jsx("td",{children:a("routes.apps")}),e.jsx("td",{children:a("overview.running")})]})}),e.jsx("tbody",{children:t&&n().map((g,C)=>e.jsxs("tr",{children:[e.jsx("td",{children:e.jsxs(m,{spacing:2,children:[e.jsx(V,{shape:"square",src:G+g.path+"/rest/appicon"}),e.jsxs("div",{children:[g.name,e.jsx("small",{children:g.path})]})]})}),e.jsx("td",{children:g.value})]},g.name))})]})})})]})]})})})]})]})})},Ie=P(()=>{var a;const{overviewStore:s,logsStore:x,globalStore:o}=I(),j=U(),{t}=A(),r=n=>{let i=0;return Object.entries(n).forEach(u=>{i+=u[1]}),i},h=n=>{j.push(n)},c=n=>{x.setSpLogId(n),j.push("logs/sessionpool")},v=n=>n.drainMode?t("overview.resume"):t("overview.drainMode"),l=s.searchTerm?(a=o.paths)==null?void 0:a.filter(n=>{var i,u;return((i=n.name)==null?void 0:i.toLowerCase().includes(s.searchTerm.toLocaleLowerCase()))||((u=n.path)==null?void 0:u.toLowerCase().includes(s.searchTerm.toLocaleLowerCase()))}):o.paths;return e.jsxs(e.Fragment,{children:[e.jsxs(m,{spacing:2,children:[e.jsx(ee,{icon:"filter_list",active:s.orderBy==="sp",onClick:()=>s.setOrderBy("sp"),children:t("overview.bySPs")}),e.jsx(ee,{icon:"filter_list",active:s.orderBy==="app",onClick:()=>s.setOrderBy("app"),children:t("overview.byapps")})]}),e.jsxs("div",{className:y(re.wTabContent),children:[s.orderBy==="sp"&&e.jsx("div",{className:d.table,children:e.jsx(se,{columns:[{dataKey:"id",width:35,align:"start",label:"",className:z.actionCell,component:(n,i)=>e.jsx(F,{className:z.actionCellDropdown,buttons:[{label:t("overview.verboseMode"),icon:"info",onClick:()=>s.toggleVerbose(i,"sp"),shouldShow:!i.stopped},{label:t("routes.logs"),icon:"list_alt",onClick:()=>c(i.id)},{label:v(i),icon:"opacity",onClick:()=>s.toggleDrain(i),shouldShow:!i.stopped},{label:t("overview.stop"),icon:"power_settings_new",onClick:()=>s.stop(i.id),shouldShow:!s.hasRunningInstances(i.apps)&&!i.stopped},{label:t("overview.shutdownSessions"),icon:"power_settings_new",onClick:()=>s.shutdown(i),shouldShow:s.hasRunningInstances(i.apps)&&!i.stopped}],children:e.jsx(k,{name:"more_vert"})})},{label:t("general.name"),dataKey:"id",minWidth:200,align:"start",bSort:!0},{label:t("overview.appsRunning"),dataKey:"apps",minWidth:100,align:"start",component:n=>e.jsx("span",{className:T.wCell,style:{justifyContent:"start"},children:n?r(n):null})},{label:t("overview.maxSessions"),dataKey:"maxInstances",minWidth:100,align:"start",component:n=>e.jsx("span",{className:T.wCell,style:{justifyContent:"start"},children:n==-1?"Unlimited":n})},{label:t("overview.maxIdleSessions"),dataKey:"maxIdleInstances",minWidth:100,align:"start",component:n=>e.jsx("span",{className:T.wCell,style:{justifyContent:"start"},children:n==-1?"Unlimited":n})},{label:t("overview.idleInstances"),dataKey:"idle",minWidth:100,align:"start",component:n=>e.jsx("span",{className:T.wCell,style:{justifyContent:"start"},children:n==-1?"Unlimited":n})},{label:t("general.priority"),dataKey:"priority",minWidth:100,align:"start",bSort:!0},{label:t("general.status"),dataKey:"stopped",minWidth:100,align:"start",component:n=>n?e.jsx(_,{size:"small",color:"danger",children:t("overview.stopped")}):e.jsx(_,{size:"small",color:"success",children:t("overview.running")})},{label:t("routes.apps"),dataKey:"apps",minWidth:100,align:"start",component:n=>e.jsx(H,{dialogHeader:t("overview.availableApps"),label:Object.entries(n).length+" / "+s.servers[0].apps.length,children:e.jsxs("table",{className:d.spTable,children:[e.jsx("thead",{children:e.jsxs("tr",{children:[e.jsx("th",{children:t("sessions.app")}),e.jsx("th",{children:t("status.running")})]})}),e.jsx("tbody",{children:n?Object.entries(n).map(i=>{var f;const u=(f=o.paths)==null?void 0:f.find(g=>g.path===i[0]);return e.jsxs("tr",{children:[e.jsx("td",{children:e.jsxs(m,{spacing:2,children:[e.jsx(V,{shape:"square",src:G+i[0]+"/rest/appicon"}),e.jsxs("div",{children:[u==null?void 0:u.name,e.jsx("small",{children:i[0]})]})]})}),e.jsx("td",{children:i[1]})]},i[0])}):null})]})})}],minTableWidth:650,rowHeight:60,threshold:500,rowClassName:d.ac,collection:s.filteredSPs})}),s.orderBy==="app"&&e.jsx("div",{className:d.table,children:e.jsx(se,{columns:[{dataKey:"name",width:35,align:"start",label:"",className:z.actionCell,component:(n,i)=>e.jsx(F,{className:z.actionCellDropdown,buttons:[{label:t("general.config"),icon:"list_alt",onClick:()=>h("apps"+i.path)},{label:t("routes.sessions"),icon:"groups",onClick:()=>h("sessions"+i.path)}],children:e.jsx(k,{name:"more_vert"})})},{label:t("overview.appName"),dataKey:"name",minWidth:100,align:"start",bSort:!0},{label:t("overview.path"),dataKey:"path",minWidth:100,align:"start",bSort:!0},{label:t("overview.running"),dataKey:"runningInstances",minWidth:100,align:"start",bSort:!0},{label:t("sessions.sp_plural"),dataKey:"path",minWidth:100,align:"start",component:(n,i)=>{var u;return e.jsx(H,{dialogHeader:t("overview.sPsForApp"),label:s.appOnSP(i.path)+" / "+s.sessionPools.length,children:e.jsxs("table",{className:d.spTable,children:[e.jsx("thead",{children:e.jsxs("tr",{children:[e.jsx("td",{children:t("sessions.sp")}),e.jsx("td",{children:t("status.running")})]})}),e.jsx("tbody",{children:n?(u=s.sessionPools)==null?void 0:u.map(f=>e.jsxs("tr",{children:[e.jsx("td",{children:f.id}),e.jsx("td",{children:f.apps[i.path]})]},f.id)):null})]})})}},{label:t("general.status"),dataKey:"status",minWidth:100,align:"start",component:n=>e.jsx(ge,{status:n})}],minTableWidth:435,rowHeight:60,threshold:500,rowClassName:d.ac,collection:l})})]})]})}),Be="_root_lp6ou_1",Le="_lineChart_lp6ou_1",Ke="_axisFont_lp6ou_6",De="_chart_lp6ou_9",ze="_tooltipWrapper_lp6ou_12",S={root:Be,lineChart:Le,axisFont:Ke,chart:De,tooltipWrapper:ze},We=P(()=>{const{scalingStore:s}=I(),{t:x}=A(),o={fontSize:"12px",lineHeight:"normal"},j={hour12:!0,hour:"numeric",minute:"2-digit"},t=h=>h.toLocaleString("en-US",j),r=h=>`${he.t("sessions.time")}: ${h.toLocaleString("en-US")}`;return e.jsx("div",{className:S.root,children:e.jsxs(m,{itemsPerRow:2,children:[e.jsxs(m,{bColumn:!0,className:S.lineChart,children:[e.jsx(q,{className:S.charsHeading,variant:"h3",title:x("scaling.utilization")}),e.jsx(X,{width:"100%",height:"100%",children:e.jsxs(Y,{syncId:"scaling-chart",className:S.chart,data:s.getUtilizationStats.dataset,margin:{top:5,right:30,left:20,bottom:5},children:[e.jsxs("defs",{children:[e.jsxs("linearGradient",{id:"colorTakenSlots",x1:"0",y1:"0",x2:"0",y2:"1",children:[e.jsx("stop",{offset:"5%",stopColor:"#F5444A",stopOpacity:.8}),e.jsx("stop",{offset:"95%",stopColor:"#F5444A",stopOpacity:0})]}),e.jsxs("linearGradient",{id:"colorFreeSlots",x1:"0",y1:"0",x2:"0",y2:"1",children:[e.jsx("stop",{offset:"5%",stopColor:"#82ca9d",stopOpacity:.8}),e.jsx("stop",{offset:"95%",stopColor:"#82ca9d",stopOpacity:0})]})]}),e.jsx(Z,{strokeDasharray:"3 3",vertical:!1}),e.jsx(Q,{className:S.axisFont,dataKey:"x",tickFormatter:t,minTickGap:30}),e.jsx(J,{className:S.axisFont,tickFormatter:s.getUtilizationStats.tickFormat}),e.jsx(M,{wrapperStyle:o,labelFormatter:r,formatter:s.getUtilizationStats.tooltipFormatter}),e.jsx(O,{type:"monotone",dataKey:"takenSlots",stackId:"1",stroke:"#F5444A",fillOpacity:1,fill:"url(#colorTakenSlots)"}),e.jsx(O,{type:"monotone",dataKey:"freeSlots",stackId:"1",stroke:"#82ca9d",fillOpacity:1,fill:"url(#colorFreeSlots)"})]})})]}),e.jsxs(m,{bColumn:!0,className:S.lineChart,children:[e.jsx(q,{className:S.charsHeading,variant:"h3",title:x("scaling.status")}),e.jsx(X,{width:"100%",height:"100%",children:e.jsxs(Y,{syncId:"scaling-chart",className:S.chart,data:s.getStatusStats.dataset,margin:{top:5,right:30,left:20,bottom:5},children:[e.jsxs("defs",{children:[e.jsxs("linearGradient",{id:"colorActiveSP",x1:"0",y1:"0",x2:"0",y2:"1",children:[e.jsx("stop",{offset:"5%",stopColor:"#82ca9d",stopOpacity:.8}),e.jsx("stop",{offset:"95%",stopColor:"#82ca9d",stopOpacity:0})]}),e.jsxs("linearGradient",{id:"colorAutodrainedSP",x1:"0",y1:"0",x2:"0",y2:"1",children:[e.jsx("stop",{offset:"5%",stopColor:"#ffc658",stopOpacity:.8}),e.jsx("stop",{offset:"95%",stopColor:"#ffc658",stopOpacity:0})]}),e.jsxs("linearGradient",{id:"colorDrainedSP",x1:"0",y1:"0",x2:"0",y2:"1",children:[e.jsx("stop",{offset:"5%",stopColor:"#F5444A",stopOpacity:.8}),e.jsx("stop",{offset:"95%",stopColor:"#F5444A",stopOpacity:0})]})]}),e.jsx(Z,{strokeDasharray:"3 3",vertical:!1}),e.jsx(Q,{className:S.axisFont,dataKey:"x",tickFormatter:t,minTickGap:30}),e.jsx(J,{className:S.axisFont,tickFormatter:s.getStatusStats.tickFormat}),e.jsx(M,{wrapperStyle:o,labelFormatter:r,formatter:s.getStatusStats.tooltipFormatter}),e.jsx(O,{type:"monotone",dataKey:"drainedSP",stackId:"1",stroke:"#F5444A",fillOpacity:1,fill:"url(#colorDrainedSP)"}),e.jsx(O,{type:"monotone",dataKey:"autodrainedSP",stackId:"1",stroke:"#ffc658",fillOpacity:1,fill:"url(#colorAutodrainedSP)"}),e.jsx(O,{type:"monotone",dataKey:"activeSP",stackId:"1",stroke:"#82ca9d",fillOpacity:1,fill:"url(#colorActiveSP)"})]})})]})]})})}),He=P(()=>{var c,v,l,a;me("Overview");const{overviewStore:s,globalStore:x,scalingStore:o}=I(),{pathname:j}=ue(),{t}=A(),r=j.includes("servers"),h=j.includes("sessionpools");return w.useEffect(()=>{s.loadOverview(),x.loadPaths(),o.loadStats()},[]),ve(()=>{s.loadOverview(),x.loadPaths(),o.loadStats()},5e3),e.jsxs("div",{children:[s.isCluster&&o.isScalingEnabled()&&e.jsxs(e.Fragment,{children:[e.jsx(te,{value:o.statType==we.Extended,size:"medium",position:"right",onClick:()=>o.toggleStatType(),children:t("scaling.statType.extended")}),e.jsx(We,{})]}),!h&&e.jsxs(e.Fragment,{children:[e.jsx(ie,{title:s.isCluster?t("overview.clusters"):t("overview.server")}),e.jsx(W,{container:!0,spacing:"sm",children:(c=s.filteredServers)!=null&&c.length?(v=s.filteredServers)==null?void 0:v.map(n=>e.jsx(W,{item:!0,xs:12,md:6,children:e.jsx(Te,{server:n})},n.id)):e.jsx(ne,{message:t("overview.noServers")})})]}),!r&&s.isCluster&&e.jsxs(e.Fragment,{children:[e.jsxs(m,{justifyContent:"space-between",alignItems:"center",children:[e.jsx(ie,{title:t("sessions.sp_plural")}),e.jsxs(m,{spacing:2,children:[s.anyStoppedSP&&e.jsx(te,{value:s.showStopped,size:"medium",position:"right",onClick:()=>s.toggleShowStopped(),children:t("overview.showStopped")}),e.jsx(oe,{icon:"apps",color:s.spView==="grid"?"blue":"neutral",onClick:()=>s.setSpView("grid")}),e.jsx(oe,{icon:"list",color:s.spView==="table"?"blue":"neutral",onClick:()=>s.setSpView("table")}),s.sessionPools.length>0&&e.jsx(F,{position:"right",buttons:[{label:t("overview.drainAll"),icon:"opacity",onClick:()=>s.drainAllSessionPools()},{label:t("overview.resumePools"),icon:"restore",onClick:()=>s.resumeAllSessionPools()},s.hasRunningSessions?{label:t("overview.shutdownAllSessions"),icon:"power_settings_new",onClick:()=>s.shutdownAllSessions(),buttons:[{label:t("overview.forceKillAllSessions"),onClick:()=>s.forceKillAllSessions()}]}:{label:t("overview.stopAllPools"),icon:"power_settings_new",onClick:()=>s.stopAllSessionPools()}],children:e.jsx("span",{className:y(b.wButtonDefault,b.medium,b.neutral,b.icon),children:e.jsx(k,{name:"more_vert"})})})]})]}),e.jsxs("div",{className:y(re.wTabContent),children:[s.spView==="grid"&&e.jsx(W,{container:!0,spacing:"sm",children:(l=s.filteredSPs)!=null&&l.length?(a=s.filteredSPs)==null?void 0:a.map(n=>e.jsx(W,{item:!0,xs:12,md:6,children:e.jsx(Pe,{clusterservers:s.servers,sp:n})},n.id)):e.jsx(ne,{message:t("overview.noSps")})}),s.spView==="table"&&e.jsx(Ie,{})]})]})]})});export{He as default};
//# sourceMappingURL=Overview-1bea8f10.js.map