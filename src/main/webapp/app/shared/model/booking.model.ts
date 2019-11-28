export interface IBooking {
  id?: number;
  name?: string;
}

export class Booking implements IBooking {
  constructor(public id?: number, public name?: string) {}
}
