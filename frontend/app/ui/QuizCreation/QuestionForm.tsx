"use client"

import {Button, Input, Select, SelectItem, Switch} from "@nextui-org/react";
import {Question, questionTypes} from "@/app/lib/definitions";
import {Dispatch, SetStateAction, useState} from "react";
import AnswerForm from "@/app/ui/QuizCreation/AnswerForm";

export default function QuestionForm(props: { setQuestions: Dispatch<SetStateAction<Question[]>> }) {

    const [state, setState] = useState<Question>({
        question: "",
        questionType: "TRUE_FALSE",
        points: 1,
        isNegated: false,
        answers: []
    })

    const handleSaveQuestion = () => {
        props.setQuestions((prevState) => ([...prevState, state]))
        setState({
            question: "",
            questionType: "TRUE_FALSE",
            points: 1,
            isNegated: false,
            answers: []
        })
    }

    return (
        <div className="w-full h-full flex flex-col gap-5 rounded-2xl shadow-xl p-5">
            <Input
                name="question"
                label="Question"
                isRequired
                value={state.question}
                onChange={(e) => setState((prevState) => ({...prevState, question: e.target.value}))}

                variant="underlined"
            />

            <Input
                type="number"
                min={1}
                value={state?.points as unknown as string}
                name="points"
                label="Points"
                isRequired

                onChange={(e) => setState((prevState) => ({...prevState, points: parseInt(e.target.value)}))}

                variant="underlined"
            />

            <Select
                name="questionType"
                label="Question type"
                isRequired

                selectedKeys={[state?.questionType]}
                onSelectionChange={
                    (e) => setState((prevState) => ({...prevState, questionType: e.anchorKey || "TRUE_FALSE"}))
                }
            >
                {questionTypes.map((type, _) => {
                    return (
                        <SelectItem key={type.value} value={type.value}>{type.label}</SelectItem>
                    )
                })
                }
            </Select>

            {state.questionType === 'TRUE_FALSE' &&
                <Switch
                    name="isNegated"
                    color="success"

                    isSelected={state.isNegated}

                    onChange={() => setState((prevState) => ({...prevState, isNegated: !prevState.isNegated}))}
                >
                    {state.isNegated ? "False" : "True"} is the correct answer!
                </Switch>}

            <AnswerForm type={state.questionType} setItems={setState}/>
            <Button
                onClick={handleSaveQuestion}
                disabled={state.question.length === 0 || (state.questionType !== "TRUE_FALSE" && state.answers.length === 0)}
                color={"danger"}
            >
                Save question
            </Button>
        </div>
    )
}