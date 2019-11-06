import { ILocation } from 'app/shared/model/location.model';
import { IContact } from 'app/shared/model/contact.model';
import { IInsurance } from 'app/shared/model/insurance.model';
import { IBooking } from 'app/shared/model/booking.model';
import { CountryEnum } from 'app/shared/model/enumerations/country-enum.model';

export interface ICustomer {
  id?: number;
  company?: string;
  firstName?: string;
  lastName?: string;
  email?: string;
  dot?: number;
  mc?: number;
  phoneNumber?: number;
  address?: string;
  streetAddress?: string;
  city?: string;
  stateProvince?: string;
  country?: CountryEnum;
  postalCode?: string;
  billingAddress?: ILocation;
  contact?: IContact;
  insurance?: IInsurance;
  bookings?: IBooking[];
}

export class Customer implements ICustomer {
  constructor(
    public id?: number,
    public company?: string,
    public firstName?: string,
    public lastName?: string,
    public email?: string,
    public dot?: number,
    public mc?: number,
    public phoneNumber?: number,
    public address?: string,
    public streetAddress?: string,
    public city?: string,
    public stateProvince?: string,
    public country?: CountryEnum,
    public postalCode?: string,
    public billingAddress?: ILocation,
    public contact?: IContact,
    public insurance?: IInsurance,
    public bookings?: IBooking[]
  ) {}
}
