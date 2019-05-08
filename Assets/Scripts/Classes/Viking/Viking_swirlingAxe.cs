using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Viking_swirlingAxe : MonoBehaviour {


    public Transform playerTransform;

    public bool faceRight;
    public bool returning;
    public float movement_speed = 8f;
    public float return_speed = 14f;
    public int damage = 5;

    

    // Update is called once per frame
    void Update () {

        if(!faceRight && transform.localScale.x > 0)
            transform.localScale = new Vector3( - transform.localScale.x, transform.localScale.y, transform.localScale.z);
        else if(faceRight && transform.localScale.x < 0)
            transform.localScale = new Vector3(-transform.localScale.x, transform.localScale.y, transform.localScale.z);

        if (returning)
        {  
            transform.position = Vector3.MoveTowards(transform.position, playerTransform.position, return_speed * Time.deltaTime);
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
        Debug.Log("Intersected with " + collision.gameObject.name);

        if (collision.gameObject.CompareTag("Wall") || collision.gameObject.CompareTag("Platform"))
        {
            returning = true;
        }

        if (collision.gameObject.CompareTag("Player") && playerTransform == collision.transform)
        {
            returning = false;
            gameObject.SetActive(false);
        }
        else if (collision.gameObject.CompareTag("Player"))
        {
            returning = true;
        }
    }

    private void OnTriggerStay2D(Collider2D collision)
    {
        Debug.Log("Intersected with " + collision.gameObject.name);

        if (collision.gameObject.CompareTag("Wall") || collision.gameObject.CompareTag("Platform"))
        {
            returning = true;
        }

        if (collision.gameObject.CompareTag("Player") && playerTransform == collision.transform)
        {
            returning = false;
            gameObject.SetActive(false);
        }
        else if (collision.gameObject.CompareTag("Player"))
        {
            returning = true;
        }
    }

    private void OnCollisionEnter2D(Collision2D collision)
    {
        Debug.Log("Intersected with " + collision.gameObject.name + " with tag: " + collision.gameObject.tag);

        if (collision.gameObject.CompareTag("Wall") || collision.gameObject.CompareTag("Platform"))
        {
            returning = true;
        }

        if (collision.gameObject.CompareTag("Player") && playerTransform == collision.transform)
        {
            returning = false;
            gameObject.SetActive(false);
        }
        else if(collision.gameObject.CompareTag("Player"))
        {
            returning = true;
        }

    }



    
}
