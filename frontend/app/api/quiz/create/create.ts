"use server"

import {cookies} from "next/headers";
import {AUTHORIZATION, Quiz} from "@/app/lib/definitions";

export default async function createQuiz(formData: Quiz) {

    return fetch("http://backend:8080/api/quiz/create", {
        method: "POST",
        body: JSON.stringify(formData),
        headers: {
            'Authorization': `Bearer ${cookies().get(AUTHORIZATION)?.value}`,
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.status < 400)
        .catch((error) => console.error('Error:', error))
}