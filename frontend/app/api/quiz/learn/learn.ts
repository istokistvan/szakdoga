"use server"

import {cookies} from "next/headers";
import {AUTHORIZATION} from "@/app/lib/definitions";

export async function allQuizzes() {
    return fetch("http://backend:8080/api/study/all", {
        method: "GET",
        cache: "no-store",
        headers: {
            'Authorization': `Bearer ${cookies().get(AUTHORIZATION)?.value}`,
        }
    })
        .then(response => response.json())
        .catch((error) => console.error('Error:', error))
}

export async function getQuiz(id: string) {
    return fetch(`http://backend:8080/api/study/${id}`, {
        method: "GET",
        cache: "no-store",
        headers: {
            'Authorization': `Bearer ${cookies().get(AUTHORIZATION)?.value}`
        }
    })
        .then(response => response.json())
        .catch((error) => console.error('Error:', error))
}