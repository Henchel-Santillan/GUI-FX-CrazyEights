package server;

public class ServerProtocol {

	public enum State {
		WAITING(0), SENT(1), UNKNOWN(2);
		
		private final int id;
		
		private State(int id) {
			this.id = id;
		}
		
		public int getId() {
			return id;
		}
	}
	
	public void process(String input) {
		
	}
}
