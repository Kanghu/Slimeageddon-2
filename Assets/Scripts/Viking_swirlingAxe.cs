using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Viking_swirlingAxe : MonoBehaviour {


    Transform playerTransform;

    public bool faceRight;
    public bool returning;
    public float movement_speed = 8f;
    public int damage = 5;

	
	// Update is called once per frame
	void Update () {

        if (returning)
        {  
            transform.position = Vector3.MoveTowards(transform.position, playerTransform.position, 14f * Time.deltaTime);
        }

        else
        {

            if (faceRight)
            {
                transform.localPosition = new Vector3(transform.localPosition.x + movement_speed * Time.deltaTime, transform.localPosition.y, transform.localPosition.z);
            }

            else
            {
                transform.localPosition = new Vector3(transform.localPosition.x - movement_speed * Time.deltaTime, transform.localPosition.y, transform.localPosition.z);
               
            }

           
        }

    }

    // Return on collision with Walls
    private void OnTriggerEnter2D(Collider2D collision)
    {
        if (collision.gameObject.tag == "Wall")
        {
            returning = true;
        }
    }

    private void OnCollisionEnter2D(Collision2D collision)
    {
        if (collision.gameObject.tag == "Wall")
        {
            returning = true;
        }
    }

}
