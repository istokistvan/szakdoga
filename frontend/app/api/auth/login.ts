"use server"

import {cookies} from "next/headers";
import {AUTHORIZATION} from "@/app/lib/definitions";

export async function handleLogin(formData: FormData) {
    return fetch("http://localhost:8080/api/auth/login", {
        method: "POST",
        body: formData,
    })
        .then(res => {
            if (res.status === 200) {
                return res.text()
            }
        }).then(data => {
            if (data) {
                cookies().set(AUTHORIZATION, data)
            }
        })
        .catch(err => console.error(err))
}