using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CameraScript : MonoBehaviour {

    public float dampTime = 0.15f;
    public bool followParent = false;
    private Vector3 velocity = Vector3.zero;
    public Transform target = null;

    public bool border;
    public float minX;
    public float maxX;
    public float minY;
    public float maxY;

    private void Start()
    {
        if (followParent)
        {
            GetComponent<Transform>().position = new Vector3(GetComponentInParent<RectTransform>().position.x, GetComponentInParent<RectTransform>().position.y, GetComponent<Transform>().position.z);
        }

    }

    // Update is called once per frame
    void FixedUpdate()
    {
        if (target)
        {
            Vector3 point = GetComponent<Camera>().WorldToViewportPoint(target.position);
            Vector3 delta = target.position - GetComponent<Camera>().ViewportToWorldPoint(new Vector3(0.5f, 0.5f, point.z));
            Vector3 destination = transform.position + delta;

            transform.position = Vector3.SmoothDamp(transform.position, destination, ref velocity, dampTime); // Damps camera towards character
        }

        if (border && !followParent)
        {
            transform.position = new Vector3(Mathf.Clamp(transform.position.x, minX, maxX), Mathf.Clamp(transform.position.y, minY, maxY), transform.position.z); // Keeps camera in border
        }

    }

    public void setTarget(Transform tg)
    {
        target = tg; // Set target to follow
    }

}
