"use client"

import {useParams, useRouter} from "next/navigation";
import {useCallback, useEffect, useState} from "react";
import {checkPassword, getQuizById, submitQuiz} from "@/app/api/quiz/examine/examine";
import {Examine, QuizResult} from "@/app/lib/definitions";
import {Button, Checkbox, Input, Radio, RadioGroup, Textarea} from "@nextui-org/react";

export default function Fill() {

    const {id} = useParams<{ id: string }>()
    const router = useRouter()

    const [state, setState] = useState<Examine>()

    useEffect(() => {
        getQuizById(id)
            .then((data) => {
                if (data.password) {
                    const password = prompt("Enter the password")
                    password && checkPassword(id, password)
                        .then((res) => {
                            if (res !== 200) {
                                router.push("/quiz/examine")
                            }
                        })
                }
                setState(data)
            })
            .catch(() => {
                router.push("/quiz/examine")
            })
    }, []);

    const renderQuestions = useCallback(() => {
        return state?.questions.map((question, qindex) => (
            <div
                key={qindex}
                className="w-2/3 h-fit flex flex-col gap-5 rounded-xl shadow-xl p-5"
            >
                <p>{question.question} ({question.points} point)</p>
                <div
                    className="flex flex-col gap-5"
                >
                    {
                        question.questionType === "TEXT" ?
                            <Textarea
                                name={question.id}
                            />
                            :
                            question.questionType === "NUMBER" ?
                                <Input
                                    type="number"
                                    name={question.id}
                                />
                                :
                                question.questionType === "MULTIPLE_CHOICE" ?
                                    question.answers.map((answer, aindex) => (
                                        <Checkbox
                                            key={aindex}
                                            name={question.id}
                                            value={answer.id + ""}
                                        >
                                            {answer.answer}
                                        </Checkbox>
                                    ))
                                    :
                                    <RadioGroup name={question.id}>
                                        {question.answers.map((answer, aindex) => (
                                            <Radio
                                                key={aindex}
                                                value={answer.id + ""}

                                            >
                                                {answer.answer}
                                            </Radio>
                                        ))}
                                    </RadioGroup>
                    }
                </div>
            </div>
        ))
    }, [state])

    const handleSubmit = (formData: FormData) => {

        if (!confirm("Are you sure you want to submit the quiz?")) {
            return
        }

        const res: QuizResult = {
            quizId: state?.id + "",
            userAnswers: []
        }

        formData.forEach((name, value) => {
            res.userAnswers.push({
                questionId: value as string,
                answer: name as string
            })
        })

        submitQuiz(res)
            .then((res) => {
                router.replace("/dashboard")
                alert(res)
            })
    }

    return (
        <form
            className="w-full h-full flex flex-col gap-5 justify-center items-center"
            action={handleSubmit}
        >
            <p>{state?.description}</p>
            {renderQuestions()}
            <Button type="submit">Submit</Button>
        </form>
    )
}