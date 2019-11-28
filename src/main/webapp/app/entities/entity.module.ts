import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'booking',
        loadChildren: () => import('./booking/booking.module').then(m => m.JiotmsappBookingModule)
      },
      {
        path: 'invoice',
        loadChildren: () => import('./invoice/invoice.module').then(m => m.JiotmsappInvoiceModule)
      },
      {
        path: 'invoice-item',
        loadChildren: () => import('./invoice-item/invoice-item.module').then(m => m.JiotmsappInvoiceItemModule)
      },
      {
        path: 'insurance',
        loadChildren: () => import('./insurance/insurance.module').then(m => m.JiotmsappInsuranceModule)
      },
      {
        path: 'contact',
        loadChildren: () => import('./contact/contact.module').then(m => m.JiotmsappContactModule)
      },
      {
        path: 'booking-item',
        loadChildren: () => import('./booking-item/booking-item.module').then(m => m.JiotmsappBookingItemModule)
      },
      {
        path: 'equipment',
        loadChildren: () => import('./equipment/equipment.module').then(m => m.JiotmsappEquipmentModule)
      },
      {
        path: 'customer',
        loadChildren: () => import('./customer/customer.module').then(m => m.JiotmsappCustomerModule)
      },
      {
        path: 'vendor',
        loadChildren: () => import('./vendor/vendor.module').then(m => m.JiotmsappVendorModule)
      },
      {
        path: 'container',
        loadChildren: () => import('./container/container.module').then(m => m.JiotmsappContainerModule)
      },
      {
        path: 'driver',
        loadChildren: () => import('./driver/driver.module').then(m => m.JiotmsappDriverModule)
      },
      {
        path: 'location',
        loadChildren: () => import('./location/location.module').then(m => m.JiotmsappLocationModule)
      },
      {
        path: 'region',
        loadChildren: () => import('./region/region.module').then(m => m.JiotmsappRegionModule)
      },
      {
        path: 'country',
        loadChildren: () => import('./country/country.module').then(m => m.JiotmsappCountryModule)
      },
      {
        path: 'department',
        loadChildren: () => import('./department/department.module').then(m => m.JiotmsappDepartmentModule)
      },
      {
        path: 'task',
        loadChildren: () => import('./task/task.module').then(m => m.JiotmsappTaskModule)
      },
      {
        path: 'employee',
        loadChildren: () => import('./employee/employee.module').then(m => m.JiotmsappEmployeeModule)
      },
      {
        path: 'job',
        loadChildren: () => import('./job/job.module').then(m => m.JiotmsappJobModule)
      },
      {
        path: 'job-history',
        loadChildren: () => import('./job-history/job-history.module').then(m => m.JiotmsappJobHistoryModule)
      },
      {
        path: 'load-order',
        loadChildren: () => import('./load-order/load-order.module').then(m => m.JiotmsappLoadOrderModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class JiotmsappEntityModule {}
