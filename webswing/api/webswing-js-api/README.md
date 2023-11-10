# Webswing API

NPM module for easier integration of Webswing  to javascript/typescript projects

It allows easier integration of Webswing into the existing web project. Module saves time for developers and it has typescript declarations.

**Requirements**: This module works with Webswing 22.2 and newer. Please use the latest respective major version of this module matching your Webswing server installation.

For further details please see: https://www.webswing.org/docs/latest/integrate/javascript-api

## Installation

```bash
npm install webswing-api
```

## Basic Usage 

```js
import { getWebswingApi } from 'webswing-api'

getWebswingApi("http://localhost:8080/webswing-demo").then((api) => {
    const instance = api.bootstrap(document.getElementById('#rootElement'))
    instance.start()
})
```

The`getWebswingApi` is an async functions that is responsible for loading the actual Webswing JS client from the Webswing server identified by the connectionUrl parameter. When the JS client is loaded API object is returned, which can be used to bootstrap the Webswing view to a DOM element passed in the first argument. 
