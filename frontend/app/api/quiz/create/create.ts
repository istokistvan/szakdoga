"use server"

import {cookies} from "next/headers";
import {AUTHORIZATION, Quiz} from "@/app/lib/definitions";

export default async function createQuiz(formData: Quiz) {
    return fetch("http://localhost:8080/api/quiz/create", {
        method: "POST",
        body: JSON.stringify(formData),
        headers: {
            'Authorization': `Bearer ${cookies().get(AUTHORIZATION)?.value}`,
            'Content-Type': 'application/json',
        }
    })
        .catch((error) => console.error('Error:', error))
}