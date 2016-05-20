package ro.pub.cs.systems.eim.practicaltest02;

public class TimeInformation {

	String hours;
	String date;
	
	public TimeInformation(String s) {
		this.date = s;
	}
	
	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public String getMinutes() {
		return minutes;
	}

	public void setMinutes(String minutes) {
		this.minutes = minutes;
	}

	public String getSeconds() {
		return seconds;
	}

	public void setSeconds(String seconds) {
		this.seconds = seconds;
	}

	String minutes;
	String seconds;
	
	public TimeInformation(String h, String m, String s) {
        this.hours = h;
        this.minutes = m;
        this.seconds = s;
    }
	
	
	public String getData(){
		return this.date;
	}
	@Override
    public String toString() {
        return "HOURS" + ": " + hours + "\n\r" +
                "MINUTES" + ": " + minutes + "\n\r" +
                "SECONDS" + ": " + seconds + "\n\r";
    }
}
