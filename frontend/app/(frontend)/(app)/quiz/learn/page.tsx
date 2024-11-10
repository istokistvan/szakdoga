"use client"

import {useCallback, useEffect, useState} from "react";
import {allQuizzes} from "@/app/api/quiz/learn/learn";
import {Button, Card, CardBody, CardFooter, CardHeader, Divider, Link} from "@nextui-org/react";
import {Study} from "@/app/lib/definitions";

export default function Learn() {

    const [state, setState] = useState([])

    useEffect(() => {
        allQuizzes()
            .then(r => setState(r))
    }, []);

    const renderQuizzes = useCallback(() => {
        return state.map((quiz: Study, index) => (
            <Card key={index} className="w-fit min-w-[400px] h-fit">
                <CardHeader
                    className="flex justify-center text-xl font-bold"
                >
                    {quiz.name}
                </CardHeader>

                <Divider/>

                {quiz.description &&
                    <CardBody>
                        <p>{quiz.description}</p>
                    </CardBody>}

                <Divider/>

                <CardFooter className="flex justify-around">
                    <Link
                        href={`/quiz/learn/continuous/${quiz.id}`}
                    >
                        <Button>Continuous</Button>
                    </Link>

                    <Link
                        href={`/quiz/learn/flipcard/${quiz.id}`}
                    >
                        <Button>Flip cards</Button>
                    </Link>
                </CardFooter>
            </Card>
        ))
    }, [state]);

    return (
        <div className="w-full h-fit flex flex-wrap gap-5 justify-between">
            {renderQuizzes()}
        </div>
    )
}