import { ITranslations, IWebswingApi, IWebswingApiFactory } from "./types";
export declare function getWebswingApi(connectionUrl: string, i18n?: ITranslations): Promise<IWebswingApi>;
export declare function getWebswingApiFactory(connectionUrl: string): Promise<IWebswingApiFactory>;
