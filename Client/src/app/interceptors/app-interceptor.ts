// Angular and RxJS imports for HTTP interception
import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { AuthService } from '../services/auth.service';
import { Observable } from 'rxjs';

/**
* AppInterceptor handles HTTP request interception
* Automatically adds authentication token to requests if available
*/
@Injectable()
export class AppInterceptor implements HttpInterceptor {
 constructor(private authService: AuthService) {}

 /**
  * Intercepts HTTP requests to add authorization header
  * @param request The outgoing HTTP request
  * @param next The next interceptor in the chain
  * @returns Observable of the HTTP event stream
  */
 intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
   const token = this.authService.getToken();
   // Add Bearer token to request headers if token exists
   if (token) {
     request = request.clone({
       setHeaders: { Authorization: `Bearer ${token}` }
     });
   }
   return next.handle(request);
 }
}