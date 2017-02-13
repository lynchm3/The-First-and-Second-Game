package com.marklynch.objects.units;

import java.util.Comparator;

import com.marklynch.objects.GameObject;
import com.marklynch.objects.weapons.Weapon;

public class Fight {
	public Actor attacker;
	public Weapon attackerWeapon;
	public GameObject defender;
	public Weapon defenderWeapon;
	public float range;

	public float damageTakenByAttacker;
	public float damageTakenByDefender;

	public float damageTakenByAttackerMultiplier;
	public float damageTakenByDefenderMultiplier;

	public int chanceOfHittingAttacker;
	public int chanceOfHittingDefender;

	public boolean reachable;

	public Advantage advantage = Advantage.NO_ADVANTAGE;

	public enum Advantage {
		NO_ADVANTAGE, ATTACKER_ADVANTAGE, DEFENDER_ADVANTAGE
	};

	public Fight(Actor attacker, Weapon attackerWeapon, GameObject target, Weapon defenderWeapon, float range) {
		this.attacker = attacker;
		this.attackerWeapon = attackerWeapon;
		this.defender = target;
		this.defenderWeapon = defenderWeapon;
		this.range = range;
		projectOutcome();
	}

	private void projectOutcome() {
		if (defenderWeapon == null) {
			damageTakenByAttacker = 0;
		} else {
			damageTakenByAttacker = defenderWeapon.damage;
			if (damageTakenByAttacker > attacker.remainingHealth)
				damageTakenByAttacker = attacker.remainingHealth;// maybe dont
																	// limit
																	// this here
		}

		if (attackerWeapon == null) {
			damageTakenByDefender = 0;
		} else {
			damageTakenByDefender = attackerWeapon.damage;
			if (damageTakenByDefender > defender.remainingHealth)
				damageTakenByDefender = defender.remainingHealth;// maybe dont
																	// limit
																	// this here
		}

		damageTakenByAttackerMultiplier = 1;
		damageTakenByDefenderMultiplier = 1;
		chanceOfHittingAttacker = 99;
		chanceOfHittingDefender = 99;

		if (defender.squareGameObjectIsOn.weaponsThatCanAttack.contains(attackerWeapon)) {
			reachable = true;
		} else {
			reachable = false;
		}

		this.advantage = Advantage.ATTACKER_ADVANTAGE;
	}

	// Order by battles by
	// 1. You don't die
	// 3. You take no damage
	// 2. You kill the enemy
	// 4. Best damage diff
	public static class FightComparator implements Comparator {
		@Override
		public int compare(Object arg0, Object arg1) {

			Fight fight0 = (Fight) arg0;
			Fight fight1 = (Fight) arg1;

			// 1. You don't die
			if (fight0.damageTakenByAttacker < fight0.attacker.remainingHealth
					&& fight1.damageTakenByAttacker >= fight1.attacker.remainingHealth)
				return -1;

			if (fight0.damageTakenByAttacker >= fight0.attacker.remainingHealth
					&& fight1.damageTakenByAttacker < fight1.attacker.remainingHealth)
				return 1;

			// 2. You kill the enemy
			if (fight0.damageTakenByDefender >= fight0.defender.remainingHealth
					&& fight1.damageTakenByDefender < fight1.defender.remainingHealth)
				return -1;

			if (fight0.damageTakenByDefender < fight0.defender.remainingHealth
					&& fight1.damageTakenByDefender >= fight1.defender.remainingHealth)
				return 1;

			// 3. You take no damage
			if (fight0.damageTakenByAttacker <= 0 && fight1.damageTakenByAttacker > 0)
				return -1;

			if (fight0.damageTakenByAttacker > 0 && fight1.damageTakenByAttacker <= 0)
				return 1;

			// 4. Best damage diff
			float fight0DamageDifference = fight0.damageTakenByAttacker - fight0.damageTakenByDefender;

			float fight1DamageDifference = fight1.damageTakenByAttacker - fight1.damageTakenByDefender;

			if (fight0DamageDifference > fight1DamageDifference)
				return -1;

			if (fight0DamageDifference < fight1DamageDifference)
				return 1;

			return 0;
		}
	}
}
