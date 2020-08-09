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
	
	private State state;
	
	public ServerProtocol() {
		state = State.WAITING;
	}
	
	public ServerProtocol(State state) {
		this.state = state;
	}
	
	public void process(String input) {
		
		switch (state) {
			case WAITING:
			case SENT:
			case UNKNOWN:
			default: 
				System.err.println("Unexpected input.");
				break;
		}
	}
}
