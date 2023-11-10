"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.getWebswingApiFactory = exports.getWebswingApi = void 0;
const inProgress = new Map();
const randomPrefix = (Math.random() + 1).toString(36).substring(7);
const globalApiRegister = self['__webswing_api_module__'] = self['__webswing_api_module__'] || { api: {} };
const apiRegister = globalApiRegister.api = globalApiRegister["api"] || {};
function getWebswingApi(connectionUrl, i18n) {
    return getWebswingApiFactory(connectionUrl).then(factory => {
        return factory.createWebswingApi(i18n);
    });
}
exports.getWebswingApi = getWebswingApi;
function getWebswingApiFactory(connectionUrl) {
    connectionUrl = withTrailingSlash(connectionUrl);
    return loadVersion(connectionUrl).then(version => {
        if (!apiRegister[version]) {
            return new Promise((resolve, reject) => {
                const url = connectionUrl + 'javascript/webswing-embed.js?version=' + version;
                loadScript(url, (event) => {
                    if (isEvent(event)) {
                        if (event.type === 'error') {
                            reject("Failed to load script at " + url);
                        }
                        else if (!apiRegister[version]) {
                            reject("Failed to load webswingApi due to unexpected or missing version. Expected version[" + version + "], loaded versions [" + Object.keys(apiRegister).join(',') + "]");
                        }
                        else {
                            resolve(apiRegister[version](connectionUrl));
                        }
                    }
                    else {
                        reject("Failed to load " + connectionUrl + '/javascript/webswing-embed.js due to ' + event);
                    }
                }, version);
            });
        }
        else {
            return apiRegister[version](connectionUrl);
        }
    });
}
exports.getWebswingApiFactory = getWebswingApiFactory;
function withTrailingSlash(url) {
    return !url.endsWith('/') ? url + '/' : url;
}
function loadVersion(connectionUrl) {
    var url = connectionUrl + "rest/version";
    return fetch(url).then((val) => {
        return val.text();
    }).catch(reason => {
        console.error("Failed to resolve version from url " + url);
        return 'undefined';
    });
}
function isEvent(eventOrString) {
    return eventOrString.type !== undefined;
}
const loadScript = (url, done, key) => {
    const inProgressUrl = inProgress.get(url);
    if (inProgressUrl != undefined) {
        inProgressUrl.push(done);
        return;
    }
    let script;
    let needAttach = false;
    if (key !== undefined) {
        let scripts = document.getElementsByTagName("script");
        for (let i = 0; i < scripts.length; i++) {
            let s = scripts[i];
            if (s.getAttribute("src") == url || s.getAttribute("data-webswing-script") == randomPrefix + key) {
                script = s;
                break;
            }
        }
    }
    if (!script) {
        needAttach = true;
        script = document.createElement('script');
        script.setAttribute("data-webswing-script", randomPrefix + key);
        script.src = url;
    }
    inProgress.set(url, [done]);
    var onScriptComplete = (prev, event) => {
        if (script) {
            script.onerror = script.onload = null;
            clearTimeout(timeout);
            var doneFns = inProgress.get(url);
            inProgress.delete(url);
            script.parentNode && script.parentNode.removeChild(script);
            doneFns && doneFns.forEach((fn) => (fn(event)));
            if (prev)
                return prev.apply(script, event);
        }
    };
    var timeout = setTimeout(onScriptComplete.bind(script, null, 'timeout'), 120000);
    script.onerror = onScriptComplete.bind(script, script.onerror);
    script.onload = onScriptComplete.bind(script, script.onload);
    needAttach && document.head.appendChild(script);
};
