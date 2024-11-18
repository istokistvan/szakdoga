"use client"

import {useParams, useRouter} from "next/navigation";
import {useCallback, useEffect, useState} from "react";
import {Study} from "@/app/lib/definitions";
import {getQuiz} from "@/app/api/quiz/learn/learn";
import {Button} from "@nextui-org/react";
import FlipCard from "@/app/ui/FlipCard/FlipCard";
import {IoIosArrowBack as BackArrow, IoIosArrowForward as ForwardArrow} from "react-icons/io";

export default function StudyFlipCard() {

    const {id} = useParams<{ id: string }>()
    const [quiz, setQuiz] = useState<Study>()
    const [index, setIndex] = useState<number>(0)

    const router = useRouter()

    useEffect(() => {
        getQuiz(id)
            .then((data) => {
                if (typeof data !== "object") {
                    router.replace("/quiz/learn")
                }
                setQuiz(data)
            })
            .catch(() => router.replace("/quiz/learn"))
    }, [id, router])

    const renderFlipCard = useCallback(() => (
        quiz &&
        <div
            className="flex gap-5 items-center justify-around"
        >
            <Button
                disabled={index === 0}
                onClick={() => setIndex((prevState) => prevState - 1)}
            >
                <BackArrow/>
            </Button>
            <FlipCard
                question={quiz.questions[index].question}
                answer={
                    quiz.questions[index].answers.filter((answer) => answer.isCorrect).map((answer) => answer.answer)
                }
            />
            <Button
                disabled={index === quiz.questions.length - 1}
                onClick={() => setIndex((prevState) => prevState + 1)}
            >
                <ForwardArrow/>
            </Button>
        </div>
    ), [index, quiz])

    return (
        <div
            className="flex flex-col gap-5 items-center justify-around"
        >
            <p>{quiz?.description}</p>

            {renderFlipCard()}


        </div>
    )
}