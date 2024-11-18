"use server"

import {cookies} from "next/headers";
import {AUTHORIZATION} from "@/app/lib/definitions";

export async function getFilledQuizNumber() {
    return fetch("http://backend:8080/api/dashboard/filled", {
        method: "GET",
        headers: {
            'Authorization': `Bearer ${cookies().get(AUTHORIZATION)?.value}`
        }
    })
        .then(response => {
            return response.text()
        })
        .catch((error) => console.error('Error:', error))
}

export async function getCreatedQuizzes() {
    return fetch("http://backend:8080/api/dashboard/quizzes", {
        method: "GET",
        headers: {
            'Authorization': `Bearer ${cookies().get(AUTHORIZATION)?.value}`
        }
    })
        .then(response => {
            return response.json()
        })
        .catch((error) => console.error('Error:', error))
}
