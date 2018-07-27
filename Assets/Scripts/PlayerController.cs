﻿using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.Networking;

public class PlayerController : NetworkBehaviour {

    public RectTransform healthBar;

    Animator anim;
    NetworkAnimator network_anim;

    [SyncVar(hook = "Turn")]
    public bool faceRight;


    // Player characteristics
    [SyncVar(hook = "HealthChange")]
    public int hp;

    public int mana;
    public int full_hp;
    public int full_mana;

    public float movement_speed = 3.0f;
    public float attack_speed;

    public float jump_velocity = 6f;

    public int hit_damage = 10;

    // Player states
    public float lastJump;
    public int jumps;

    // Player states

    bool recharging;
    bool dazing;
    bool immovable;

    public GameObject weapon;

	// Use this for initialization
	void Start () {

        anim = GetComponent<Animator>();
        network_anim = GetComponent<NetworkAnimator>();

        faceRight = true;

        recharging = false;
        dazing = false;
        immovable = false;
	}
	
	// Update is called once per frame
	void Update () {

        if (!isLocalPlayer)
        {
            return;
        }

        if (Input.GetKeyDown(KeyCode.L)) // For testing purposes
        {
            hp -= 10;

            if (!dazing)
            {
                anim.SetBool("dazing", true);
                dazing = true;
                immovable = true;
            }

            else
            {
                anim.SetBool("dazing", false);
                dazing = false;
                immovable = false;
            }
        }

        if (immovable)
            return;

        var dirVec = new Vector2(Input.GetAxisRaw("Horizontal"), Input.GetAxisRaw("Vertical")); // Input
        int input_x = (int) dirVec.x * 100; // In order to be compared in the State Machine
        int input_y = (int) dirVec.y * 100;

        anim.SetInteger("input_x", input_x);

        if(input_x < 0) // Moves towards left
        {
            if(faceRight)
            {
                faceRight = !faceRight;

                if (!isServer) // If it isn't the server, faceRight needs to be updated on server in order for the hook to be called
                    CmdTurn();
            }
        }

        else if(input_x > 0) // Moves towards right
        {
            if (!faceRight)
            {
                faceRight = !faceRight;

                if (!isServer) // If it isn't the server, faceRight needs to be updated on server in order for the hook to be called
                    CmdTurn();
            }
        }

        if ((Input.GetKeyDown(KeyCode.Space) || input_y > 0))
        {
            if (Time.time - lastJump > 0.25f && jumps < 2) // Check for cooldown and consecutive jumps
            {
                anim.SetTrigger("jumping");
                //GetComponent<Animator>().Play("Jumping", 0, 0);

                GetComponent<Rigidbody2D>().velocity = new Vector2(0, jump_velocity); // Add force

                jumps++;
                lastJump = Time.time;
            }
        }

        if(Input.GetMouseButtonDown(0))
        {
            Attack();
        }


        if (Input.GetKeyDown(KeyCode.S))
        {
            anim.SetBool("recharging", true);

            recharging = true;
        }

        if (Input.GetKeyUp(KeyCode.S) && recharging)
        {
            anim.SetBool("recharging", false);

            recharging = false;
        }

        transform.Translate(dirVec.x * Time.deltaTime * movement_speed, 0, 0); // Movement

        healthBar.sizeDelta = new Vector2(
        hp,
        healthBar.sizeDelta.y);
    }

    private void OnCollisionEnter2D(Collision2D collision)
    {
        if(collision.gameObject.tag == "Platform")
        {
            anim.SetBool("jumping", false);

            jumps = 0; // Resets jumps if player collides with the floor
        }

        if (collision.gameObject.tag == "Spike")
        {
            TakeDamage(10);
        }
    }

    void Turn(bool facingRight) // Called everytime faceRight is changed (but ONLY ON SERVER!)
    {
        if(transform.localScale.x < 0 && facingRight)
        {
            transform.localScale = new Vector3(transform.localScale.x * (-1), transform.localScale.y, transform.localScale.z);
        }

        else if(transform.localScale.x > 0 && !facingRight)
        {
            transform.localScale = new Vector3(transform.localScale.x * (-1), transform.localScale.y, transform.localScale.z);
        }
    }

    [Command]
    void CmdTurn()
    {
        faceRight = !faceRight;
    }

    public override void OnStartLocalPlayer()
    {
        Camera.main.GetComponent<CameraScript>().setTarget(transform); // propria camera urmareste propriul caracter
    }

    public void TakeDamage(int amount)
    {
        if (!isServer)
            return;

        hp -= amount;
    }

    void HealthChange(int health)
    {
        hp = health; // update Server -> Client
        
        healthBar.sizeDelta = new Vector2(health, healthBar.sizeDelta.y);
    }

    void Attack()
    {
        anim.SetTrigger("attacking");
        network_anim.SetTrigger("attacking"); // trigger pentru NetworkAnimator (necesar ptr layere)
    }
}

