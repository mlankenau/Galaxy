package org.galaxy;

import org.json.simple.JSONObject;

public class Ship {
	Planet source = null;
	Planet dest =  null;
	private Party party;
	long lauchTime = 0;
	float deviation;
	Vector startPoint;
	float MAX_DEVIATION = 20.f;
	float MAX_LAUNCHING_LAG = 200;
	
	public Party getParty() {
		return party;
	}

	public void setParty(Party party) {
		this.party = party;
	}
	
	Vector pos;
	float speed;
	
	
	public long getLauchTime() {
		return lauchTime;
	}

	public float getDeviation() {
		return deviation;
	}

	public float getSpeed() {
		return speed;
	}

	public Ship(Game game, JSONObject json) {
		source = game.findPlanet(json.get("source").toString());
		dest = game.findPlanet(json.get("destination").toString());
		
				
		String partyStr = json.get("party").toString();
		if (partyStr.equals("")) 
			party = game.getNeutral();
		else if (partyStr.equals("me")) 
			party = game.getOpponent();
		else if (partyStr.equals("opponent")) 
			party = game.getMe();
		
		this.speed = Float.parseFloat(json.get("speed").toString());
		this.lauchTime = Long.parseLong(json.get("lauchTime").toString());
		this.deviation = Float.parseFloat(json.get("deviation").toString());
	}
	
	public Ship(Party party, Planet source, Planet dest, float speed, long lauchTime) {
		long launchLag = (long) (Math.random() * MAX_LAUNCHING_LAG);
		
		this.party = party;
		this.source = source;
		this.dest = dest;
		this.speed = speed;		
		this.lauchTime = lauchTime + launchLag;
		this.startPoint = source.getPos().add(dest.getPos().sub(source.getPos()).normalize().multiply(source.getSize()));
		
		this.deviation = (float) (-MAX_DEVIATION + Math.random() * 2. * MAX_DEVIATION);
		pos = source.getPos();

		Vector delta = dest.getPos().sub(pos); 
		delta = delta.normalize();
		Vector move = delta.multiply(source.getSize());		
		pos = pos.add(move);
	}
		
	public String toJson() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("{");
		buffer.append("  \"party\": \""+party.getId()+"\",");
		buffer.append("  \"source\": \""+source.getId()+"\",");
		buffer.append("  \"destination\": \""+dest.getId()+"\",");
		buffer.append("  \"speed\": "+speed+",");
		buffer.append("  \"lauchTime\": "+ lauchTime + ",");
		buffer.append("  \"deviation\": "+ deviation + "");
		buffer.append("}");
		
		return buffer.toString();
	}

	
	public void move(long time) {		
		Vector vector = dest.getPos().sub(startPoint);
		
		Vector deviationVector = new Vector(vector.y, -vector.x).normalize().multiply(deviation);			
		float timeLeft = ((float) (time - lauchTime)) / 1000.f;								
		float traveled = timeLeft * speed;
 
		deviationVector = deviationVector.multiply((float) (Math.sin(traveled / vector.length() * Math.PI)));
		pos = startPoint.add(vector.normalize().multiply(traveled)).add(deviationVector);
	}

	public Planet getSource() {
		return source;
	}

	public Planet getDest() {
		return dest;
	}
	
	public Vector getPos() {
		return pos;
	}
}
