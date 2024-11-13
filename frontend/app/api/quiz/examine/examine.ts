"use server"

import {AUTHORIZATION, QuizResult} from "@/app/lib/definitions";
import {cookies} from "next/headers";

export async function getAllQuiz() {
    return fetch("http://localhost:8080/api/examine/all", {
        method: "GET",
        headers: {
            'Authorization': `Bearer ${cookies().get(AUTHORIZATION)?.value}`
        }
    })
        .then(response => response.json())
        .catch((error) => console.error('Error:', error))
}

export async function getQuizById(id: string) {
    return fetch(`http://localhost:8080/api/examine/${id}`, {
        method: "GET",
        headers: {
            'Authorization': `Bearer ${cookies().get(AUTHORIZATION)?.value}`
        }
    })
        .then(response => response.json())
        .catch((error) => console.error('Error:', error))
}

export async function checkPassword(id: string, password: string) {
    return fetch(`http://localhost:8080/api/examine/password/${id}`, {
        method: "POST",
        headers: {
            'Authorization': `Bearer ${cookies().get(AUTHORIZATION)?.value}`
        },
        body: password
    })
        .then(response => response.status)
        .catch((error) => console.error('Error:', error))
}

export async function submitQuiz(formData: QuizResult) {
    return fetch("http://localhost:8080/api/result/submit", {
        method: "POST",
        headers: {
            'Authorization': `Bearer ${cookies().get(AUTHORIZATION)?.value}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
    })
        .then(res => res.text())
        .catch((error) => console.error(error))
}