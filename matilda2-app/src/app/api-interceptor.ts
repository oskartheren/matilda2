import { Injectable } from '@angular/core'
import {
    HttpInterceptor,
    HttpRequest,
    HttpHandler,
    HttpEvent
} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class APIInterceptor implements HttpInterceptor {
    private URL: string = "http://localhost:8080"

    intercept(req: HttpRequest<any>, next: HttpHandler):
            Observable<HttpEvent<any>> {
        return next.handle(req.clone({
            url: `${this.URL}${req.url}`
        }))
    }
}
