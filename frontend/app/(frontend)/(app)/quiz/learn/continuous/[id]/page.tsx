"use client"

import {useCallback, useEffect, useState} from "react";
import {Study} from "@/app/lib/definitions";
import {getQuiz} from "@/app/api/quiz/learn/learn";
import {useParams, useRouter} from "next/navigation";

export default function StudyContinuous() {

    const {id} = useParams<{ id: string }>()

    const [state, setState] = useState<Study>()

    const router = useRouter()

    useEffect(() => {
        getQuiz(id)
            .then(data => {
                    if (typeof data !== "object") {
                        router.replace("/quiz/learn")
                    }
                    setState(data)
                }
            )
            .catch(() => router.replace("/quiz/learn"))
    }, [id, router])

    const renderQuestions = useCallback(() => {
        return state?.questions.map((question, index) => (
            <div
                key={index}
                className="w-4/5 h-fit flex flex-col shadow-xl rounded-xl p-5"
            >
                <h2>{question.question}</h2>
                <ul>
                    {
                        question.answers.map((answer, index) => (
                            <li
                                key={index}
                                className={`${answer.isCorrect ? 'text-green-500' : 'text-red-500'}`}
                            >{answer.answer}</li>
                        ))
                    }
                </ul>
            </div>
        ))
    }, [state])

    return (
        <div
            className="w-full h-fit flex flex-col gap-5 items-center justify-center"
        >
            <p>{state?.description}</p>
            {renderQuestions()}
        </div>
    )
}