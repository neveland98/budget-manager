import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { LoginResponse } from "./login-response";
import { TokenStorageService } from "./token-storage.service";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

    constructor(private tokenStorage: TokenStorageService) {}
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        let user: LoginResponse = this.tokenStorage.getUser();
        if(user !== null) {
            req = req.clone(
                {
                    headers: req.headers.set("Authorization","Bearer " + user.token)
                }
            );
        }
        return next.handle(req);
    }

}