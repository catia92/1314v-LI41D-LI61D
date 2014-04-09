package isel.mpd.binding;

import isel.mpd.typesystem.ClassPreamble;

@ClassPreamble(
 author = "Luis Falc�o",
 date = "7-4-2014"
)
public class NonNullFieldsBinder extends NonNullBinder {

	private static FieldsBinder Binder = new FieldsBinder();
	
	public NonNullFieldsBinder() {
		super(Binder);
		
		
	}
}
