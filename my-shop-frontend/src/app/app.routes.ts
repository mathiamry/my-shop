import { Routes } from '@angular/router';
import { LayoutComponent } from './shared/layout/layout.component';
import { ProductListContainer } from './features/product/containers/product-list/product-list.component';
import { CartPageComponent } from './features/cart/containers/cart-page/cart-page.component';
import { ContactPageContainer } from './features/contact/containers/contact-page/contact-page.component';
export const routes: Routes = [
  {
    path: '',
    component: LayoutComponent,
    children: [
      { path: '', component: ProductListContainer }, // home = list
      { path: 'shop', component: ProductListContainer },
      { path: 'contact', component: ContactPageContainer },
      { path: 'cart', component: CartPageComponent },
      { path: '**', redirectTo: '' },
    ],
  },
];
