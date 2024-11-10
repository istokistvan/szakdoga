export default function FlipCard(props: { question: string, answer: string[] }) {
    return (
        <div
            className="flex flex-col justify-center"
        >
            <div
                className="group h-96 w-96 [perspective: 1000px]"
            >
                <div
                    className="relative w-full h-full rounded-xl shadow-xl [transform-style:preserve-3d] transition-all duration-500 group-hover:[transform:rotateY(180deg)]"
                >
                    <div
                        className="absolute inset-0 [backface-visibility:hidden] bg-white flex flex-col justify-center items-center"
                    >
                        <p>{props.question}</p>
                    </div>

                    <div
                        className="absolute inset-0 [backface-visibility:hidden] [transform:rotateY(180deg)] bg-white flex flex-col justify-center items-center"
                    >
                        {props.answer.map((answer, index) => (
                            <p key={index}>{answer}</p>
                        ))
                        }
                    </div>
                </div>
            </div>
        </div>
    )
}