import { useMarkdown } from '../_contexts/MarkdownContext';
import ReactMarkdown from 'react-markdown';
import remarkGfm from 'remark-gfm';
import remarkBreaks from 'remark-breaks';
import rehypeRaw from 'rehype-raw';

function GitHubMarkdownCSS() {
  return (
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/github-markdown-css/5.2.0/github-markdown.min.css"
    />
  );
}

export function MarkdownPreview() {
  const { markdowns } = useMarkdown();

  return (
    <div>
      <GitHubMarkdownCSS />
      <div className="markdown-body">
        {markdowns?.map((markdown) => (
          <div key={markdown.id}>
            <ReactMarkdown
              remarkPlugins={[remarkGfm, remarkBreaks]}
              rehypePlugins={[rehypeRaw]}
              // className="whitespace-pre-wrap p-5"
              // className="p-5"
            >
              {markdown.content}
            </ReactMarkdown>
          </div>
        ))}
      </div>
    </div>
  );
}
