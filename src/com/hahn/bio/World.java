package com.hahn.bio;

import static com.hahn.bio.util.Config.START_BOIDS;
import static com.hahn.bio.util.Config.START_BOID_ENERGY;
import static com.hahn.bio.util.Config.WORLD_SIZE;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.hahn.bio.boid.Boid;
import com.hahn.bio.plant.Plants;

public class World {
	public static final Random rand = new Random();

	public static Plants plants;
	
	private final List<Boid> mBoids;
	private ListIterator<Boid> mBoidsIt;
	
	public World() {
		plants = Plants.create();
		
		mBoids = new ArrayList<Boid>();
		
		for (int i = 0; i < START_BOIDS; i++) {
			int x = rand.nextInt(WORLD_SIZE);
			int y = rand.nextInt(WORLD_SIZE);
			
			mBoids.add(new Boid(this, x, y, START_BOID_ENERGY));
		}
	}
	
	public void draw(Graphics g) {
		plants.updateAndDraw(g);
		
		for (Boid b: mBoids) {
			b.draw(g);
		}
		
		g.setColor(Color.white);
	}
	
	public void update() {		
		mBoidsIt = mBoids.listIterator();
		while (mBoidsIt.hasNext()) {
			Boid b = mBoidsIt.next();
			b.update();
			
			// Remove if dead
			if (!b.isAlive()) {
				b.kill();
				mBoidsIt.remove();
			}
		}
	}
	
	public List<Boid> getBoids() {
		return mBoids;
	}
	
	public void addBoid(Boid b) {
		if (mBoidsIt != null) {
			mBoidsIt.add(b);
		} else {
			mBoids.add(b);
		}
	}
}
